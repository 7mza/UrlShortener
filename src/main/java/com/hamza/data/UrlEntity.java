package com.hamza.data;

import com.hamza.dtos.UrlGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "url")
@NoArgsConstructor
@Data
public class UrlEntity {

    @Id
    @Column(name = "id")
    private String shortUrl; // short url, act as id

    @Column(unique = true)
    private String fullUrl; // full url

    @CreationTimestamp
    private Instant createdAt = Instant.now(); // handled by DB

    @UpdateTimestamp
    private Instant updatedAt = Instant.now(); // handled by DB

    public UrlEntity(String shortUrl, String fullUrl) {
        this.shortUrl = shortUrl;
        this.fullUrl = fullUrl;
    }

    public static String generateUUID() { // generate random 6 chars UUID to act as shortUrl/id
        return RandomStringUtils.randomAlphanumeric(6); // 6 -> 62^6 combination possible
    }

    public UrlGetDto toDto() {
        return new UrlGetDto(this.shortUrl, this.fullUrl);
    }
}
