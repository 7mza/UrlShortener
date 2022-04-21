package com.hamza.controllers;

import com.hamza.dtos.UrlCollectionDto;
import com.hamza.dtos.UrlGetDto;
import com.hamza.dtos.UrlPostDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;

/*
 * Swagger API documentation
 */
@Api(
        tags = "short_url",
        description = "API for basic CRUD operations",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public interface IApi {

    @ApiOperation(
            value = "Generate a short from for an URL",
            notes = "Invalid URLs will be rejected. Duplicate URL will return existing short URL.",
            httpMethod = "POST",
            response = UrlGetDto.class,
            protocols = "https,http"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Successful Operation",
                            response = UrlGetDto.class,
                            examples = @Example(
                                    @ExampleProperty(
                                            value = "{\"shortUrl\": \"A1b2C3\", \"fullUrl\": \"https://www.google.fr\"}",
                                            mediaType = MediaType.APPLICATION_JSON_VALUE
                                    )
                            )
                    ),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 500, message = "Internal Error")
            })
    UrlGetDto create(UrlPostDto urlPostDto);

    @ApiOperation(
            value = "Retrieve an URL by its short form",
            notes = "this call will not redirect, use /short_url/{short_url} instead of /api/short_url/{short_url} to test redirection",
            httpMethod = "GET",
            response = UrlGetDto.class,
            protocols = "https,http"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Successful Operation",
                            response = UrlGetDto.class,
                            examples = @Example(
                                    @ExampleProperty(
                                            value = "{\"shortUrl\": \"A1b2C3\", \"fullUrl\": \"https://www.google.fr\"}",
                                            mediaType = MediaType.APPLICATION_JSON_VALUE
                                    )
                            )
                    ),
                    @ApiResponse(code = 404, message = "Not Found"),
                    @ApiResponse(code = 500, message = "Internal Error")
            })
    UrlGetDto read(String shortUrl);

    @ApiOperation(
            value = "Retrieve all URLs in DB",
            httpMethod = "GET",
            response = UrlCollectionDto.class,
            protocols = "https,http"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Successful Operation",
                            response = UrlCollectionDto.class,
                            examples = @Example(
                                    @ExampleProperty(
                                            value = "{\n" +
                                                    "  \"content\": [\n" +
                                                    "    {\n" +
                                                    "      \"shortUrl\": \"A1b2C3\",\n" +
                                                    "      \"fullUrl\": \"https://www.google.fr\"\n" +
                                                    "    }\n" +
                                                    "  ]\n" +
                                                    "}",
                                            mediaType = MediaType.APPLICATION_JSON_VALUE
                                    )
                            )
                    ),
                    @ApiResponse(code = 500, message = "Internal Error")
            })
    UrlCollectionDto read();

    @ApiOperation(
            value = "Update an URL by it's short form",
            notes = "Invalid URLs will be rejected. Duplicate URL will return existing short URL.",
            httpMethod = "PUT",
            response = UrlGetDto.class,
            protocols = "https,http"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Successful Operation",
                            response = UrlGetDto.class,
                            examples = @Example(
                                    @ExampleProperty(
                                            value = "{\"shortUrl\": \"A1b2C3\", \"fullUrl\": \"https://www.google.fr\"}",
                                            mediaType = MediaType.APPLICATION_JSON_VALUE
                                    )
                            )
                    ),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found"),
                    @ApiResponse(code = 500, message = "Internal Error")
            })
    UrlGetDto update(String shortUrl, UrlPostDto urlPostDto);

    @ApiOperation(
            value = "Delete an URL by it's short form",
            httpMethod = "DELETE",
            protocols = "https,http"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Operation"),
                    @ApiResponse(code = 404, message = "Not Found"),
                    @ApiResponse(code = 500, message = "Internal Error")
            })
    void delete(String shortUrl);
}
