package com.hamza.controllers;

import com.hamza.data.UrlEntity;
import com.hamza.dtos.UrlCollectionDto;
import com.hamza.dtos.UrlGetDto;
import com.hamza.dtos.UrlPostDto;
import com.hamza.sevices.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/short_url")
@RequiredArgsConstructor
public class UrlController implements IApi {

    private final UrlService service;

    @Override
    @PostMapping("/")
    public UrlGetDto create(@RequestBody UrlPostDto urlPostDto) {
        return service.create(urlPostDto.getFullUrl()).toDto();
    }

    @Override
    @GetMapping("/{shortUrl}")
    public UrlGetDto read(@PathVariable String shortUrl) {
        return service.read(shortUrl).toDto();
    }

    @Override
    @GetMapping("/")
    public UrlCollectionDto read() {
        return new UrlCollectionDto(service.read().stream().map(UrlEntity::toDto).collect(Collectors.toList()));
    }

    @Override
    @PutMapping("/{shortUrl}")
    public UrlGetDto update(@PathVariable String shortUrl, @RequestBody UrlPostDto urlPostDto) {
        return service.update(shortUrl, urlPostDto.getFullUrl()).toDto();
    }

    @Override
    @DeleteMapping(value = "/{shortUrl}")
    public void delete(@PathVariable String shortUrl) {
        service.delete(shortUrl);
    }
}

