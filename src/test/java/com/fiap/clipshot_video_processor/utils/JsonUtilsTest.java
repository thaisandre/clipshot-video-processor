package com.fiap.clipshot_video_processor.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonUtilsTest {

    @ParameterizedTest
    @NullAndEmptySource
    void fromJson_shouldThrowException_whenIsNotValidJsonString(String json) {
        assertThatThrownBy(() -> JsonUtils.fromJson(json, Person.class))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void fromJson_shouldReturnObjectCorrectly_whenIsValidEmptyJsonString() {
        String json = "{}";
        Person person = JsonUtils.fromJson(json, Person.class);

        assertThat(person).isNotNull();
        assertThat(person.name()).isNull();
        assertThat(person.age()).isEqualTo(0);
    }

    @Test
    void fromJson_shouldReturnObjectCorrectly_whenIsValidJsonString() {
        String json = "{\"name\":\"John\", \"age\":30}";
        Person person = JsonUtils.fromJson(json, Person.class);

        assertThat(person).isNotNull();
        assertThat(person.name()).isEqualTo("John");
        assertThat(person.age()).isEqualTo(30);
    }

    @Test
    void fromJson_shouldReturnObjectCorrectly_whenIsValidJsonBytes() {
        String json = "{\"name\":\"Jane\", \"age\":25}";
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        Person person = JsonUtils.fromJson(bytes, Person.class);

        assertThat(person).isNotNull();
        assertThat(person.name()).isEqualTo("Jane");
        assertThat(person.age()).isEqualTo(25);
    }


    @Test
    void fromJson_shouldReturnObjectCorrectly_whenIsValidEmptyJsonBytes() {
        String json = "{}";
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        Person person = JsonUtils.fromJson(bytes, Person.class);

        assertThat(person).isNotNull();
        assertThat(person.name()).isNull();
        assertThat(person.age()).isEqualTo(0);
    }

    record Person(String name, int age) {}

}