package com.hamza.controllers;

import com.hamza.dtos.UrlGetDto;
import com.hamza.sevices.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.hamza.utils.UrlUtils.HTTPS_PROTOCOL;

@RestController
@RequestMapping("/short_url")
@RequiredArgsConstructor
class RedirectCtrl {
    private final UrlService service;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        UrlGetDto urlGetDto = service.read(shortUrl).toDto();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(HTTPS_PROTOCOL + urlGetDto.getFullUrl())).build();
    }
}