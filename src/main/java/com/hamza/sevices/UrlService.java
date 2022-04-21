package com.hamza.sevices;

import com.hamza.data.UrlEntity;
import com.hamza.data.repositories.IUrlRepository;
import com.hamza.exceptions.MalformedUrlException;
import com.hamza.exceptions.ResourceNotFoundException;
import com.hamza.exceptions.UnhandledException;
import com.hamza.utils.UrlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {

    private final IUrlRepository repo;

    public UrlEntity create(String fullUrl) {
        try {
            String validatedUrl = UrlUtils.validateUrl(fullUrl); // validate and sanitize url

            // check for duplication and return existing
            Optional<UrlEntity> optional = repo.findByFullUrl(validatedUrl);
            if (optional.isPresent()) {
                return optional.get();
            }

            // check if generated UUID is already existing
            String shortUrl = UrlEntity.generateUUID();
            while (repo.findById(shortUrl).isPresent()) {
                shortUrl = UrlEntity.generateUUID();
            }

            return repo.save(new UrlEntity(shortUrl, validatedUrl));
        } catch (Exception e) {
            if (e instanceof MalformedUrlException) {
                throw e;
            } else throw new UnhandledException(ExceptionUtils.getRootCause(e).getLocalizedMessage());
        }
    }

    public UrlEntity read(String shortUrl) {
        try {
            return repo.findById(shortUrl).orElseThrow(ResourceNotFoundException::new);
        } catch (Exception e) {
            if (e instanceof ResourceNotFoundException) {
                throw e;
            } else throw new UnhandledException(ExceptionUtils.getRootCause(e).getLocalizedMessage());
        }
    }

    public List<UrlEntity> read() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            throw new UnhandledException(ExceptionUtils.getRootCause(e).getLocalizedMessage());
        }
    }

    public UrlEntity update(String shortUrl, String fullUrl) {
        try {
            String validatedUrl = UrlUtils.validateUrl(fullUrl); // validate and sanitize url

            // check for duplication and return existing
            Optional<UrlEntity> optional = repo.findByFullUrl(validatedUrl);
            if (optional.isPresent()) {
                return optional.get();
            }

            UrlEntity urlEntity = repo.findById(shortUrl).orElseThrow(ResourceNotFoundException::new);
            urlEntity.setFullUrl(validatedUrl);
            return repo.save(urlEntity);
        } catch (Exception e) {
            if (e instanceof MalformedUrlException || e instanceof ResourceNotFoundException) {
                throw e;
            } else throw new UnhandledException(ExceptionUtils.getRootCause(e).getLocalizedMessage());
        }
    }

    public void delete(String shortUrl) {
        try {
            repo.deleteById(shortUrl);
        } catch (Exception e) {
            if (e instanceof EmptyResultDataAccessException) throw new ResourceNotFoundException();
            else throw new UnhandledException(ExceptionUtils.getRootCause(e).getLocalizedMessage());
        }
    }
}
