package com.fiap.clipshot_video_processor.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

public class JsonUtils {

    private JsonUtils() {}

    public static <T> T fromJson(String json, Class<T> clazz) {
        return fromJson(json.getBytes(StandardCharsets.UTF_8), clazz);
    }

    public static <T> T fromJson(byte[] bytes, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(bytes, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
