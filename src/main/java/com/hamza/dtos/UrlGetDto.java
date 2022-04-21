package com.hamza.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UrlGetDto {
    private String shortUrl;
    private String fullUrl;
}
