package com.fiap.clipshot_video_processor.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public final class ValidationUtils {

    private ValidationUtils() {}

    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(String text, String message) {
        notNull(text, message);
        if(text.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean predicate, String message) {
        if(!predicate) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isURL(String url, String message) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException(message);
        }
    }
}
