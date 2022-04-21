package com.hamza.sevices;

import com.hamza.data.UrlEntity;
import com.hamza.data.repositories.IUrlRepository;
import com.hamza.exceptions.MalformedUrlException;
import com.hamza.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UrlEntityServiceTest {
    private static final String SHORT_URL = "A1b2C3";
    private static final String GOOGLE_URL = "https://google.fr";
    private static final String YAHOO_URL = "http://www.yahoo.fr";
    private final IUrlRepository repo;
    private final UrlService service;

    @Autowired
    UrlEntityServiceTest(IUrlRepository repo) {
        this.repo = repo;
        this.service = new UrlService(repo);
    }

    @BeforeEach
    void init() {
        repo.save(new UrlEntity(SHORT_URL, "google.fr"));
        assertThat(repo.count()).isEqualTo(1);
    }

    @AfterEach
    void clean() {
        repo.deleteAll();
        assertThat(repo.count()).isZero();
    }

    @Test
    @DisplayName("create should add 1 new URL")
    void create() {
        UrlEntity urlEntity = service.create(YAHOO_URL);
        assertThat(urlEntity.getShortUrl()).isNotBlank();
        assertThat(urlEntity.getShortUrl()).hasSize(6);
        assertThat(urlEntity.getFullUrl()).isEqualTo("yahoo.fr");
        assertThat(repo.count()).isEqualTo(2);
    }

    @Test
    @DisplayName("create should not add duplicates but return existing short url")
    void createDuplicate() {
        UrlEntity urlEntity1 = service.create(YAHOO_URL);
        UrlEntity urlEntity2 = service.create(YAHOO_URL);
        assertThat(repo.count()).isEqualTo(2);
        assertThat(urlEntity2.getShortUrl()).isEqualTo(urlEntity1.getShortUrl());
        assertThat(urlEntity2.getFullUrl()).isEqualTo(urlEntity1.getFullUrl());
    }

    @Test()
    @DisplayName("create with an invalid url should throw MalformedUrlException")
    void createInvalidUrl() {
        assertThatThrownBy(() -> service.create("toto")).isInstanceOf(MalformedUrlException.class);
    }

    @Test
    @DisplayName("read should retrieve an existing URL by it's shortUrl")
    void read() {
        UrlEntity urlEntity = service.read(SHORT_URL);
        assertThat(urlEntity.getFullUrl()).isEqualTo("google.fr");
    }

    @Test
    @DisplayName("readAll should retrieve all existing URLs")
    void readAll() {
        assertThat(service.read()).hasSize(1);
        repo.deleteAll();
        assertThat(service.read()).isEmpty();
    }

    @Test
    @DisplayName("read should throw ResourceNotFoundException if short url is not existing")
    void readNotFound() {
        assertThatThrownBy(() -> service.read("toto")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("update should modify an existing URL by it's shortUrl")
    void update() {
        service.update(SHORT_URL, YAHOO_URL);
        assertThat(repo.count()).isEqualTo(1);
        Optional<UrlEntity> optional = repo.findById(SHORT_URL);
        assertThat(optional).isPresent();
        UrlEntity urlEntity = optional.get();
        assertThat(urlEntity.getFullUrl()).isEqualTo("yahoo.fr");
    }

    @Test
    @DisplayName("update should not add duplicates but return existing short url")
    void updateDuplicate() {
        UrlEntity newUrlEntity = service.create(YAHOO_URL);
        UrlEntity modUrlEntity = service.update(newUrlEntity.getShortUrl(), GOOGLE_URL);
        assertThat(modUrlEntity.getShortUrl()).isEqualTo(SHORT_URL);
        assertThat(modUrlEntity.getFullUrl()).isEqualTo("google.fr");
    }

    @Test()
    @DisplayName("update with an invalid url should throw MalformedUrlException")
    void updateInvalidUrl() {
        assertThatThrownBy(() -> service.update(SHORT_URL, "toto")).isInstanceOf(MalformedUrlException.class);
    }

    @Test
    @DisplayName("update should throw ResourceNotFoundException if short url is not existing")
    void updateNotFound() {
        assertThatThrownBy(() -> service.update("toto", "gnews.fr")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("delete should remove an existing URL by it's shortUrl")
    void delete() {
        service.delete(SHORT_URL);
        assertThat(repo.count()).isZero();
    }

    @Test
    @DisplayName("delete should throw ResourceNotFoundException if short url is not existing")
    void deleteNotFound() {
        assertThatThrownBy(() -> service.delete("toto")).isInstanceOf(ResourceNotFoundException.class);
    }
}