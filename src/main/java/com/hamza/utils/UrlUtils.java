package com.hamza.utils;

import com.hamza.exceptions.MalformedUrlException;
import org.apache.commons.validator.routines.UrlValidator;

public class UrlUtils {

    public static final String HTTPS_PROTOCOL = "https://";

    public static final String HTTP_PROTOCOL = "http://";

    private static String prepareUrl(String url) {
        String tmp = "";
        if (url.startsWith(HTTPS_PROTOCOL)) {
            tmp = url;
        } else if (url.startsWith(HTTP_PROTOCOL)) {
            tmp = url.replaceFirst(HTTP_PROTOCOL, HTTPS_PROTOCOL);
        } else
            tmp = HTTPS_PROTOCOL + url;
        return tmp;
    }

    private static String sanitizeUrl(String url) {
        return url.replaceFirst(HTTPS_PROTOCOL, "").replaceFirst("www.", "");
    }

    public static String validateUrl(String url) throws MalformedUrlException {
        String tmp = prepareUrl(url);
        if (new UrlValidator().isValid(tmp)) {
            return sanitizeUrl(tmp);
        } else
            throw new MalformedUrlException();
    }
}
