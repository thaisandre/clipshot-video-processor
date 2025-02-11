package com.fiap.clipshot_video_processor.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidationUtilsTest {

    @Test
    void notNull__shouldThrowIllegalArgumentExceptionWithCustomMessage_whenObjectIsNull() {
        Integer integer = null;
        assertThatThrownBy(() -> ValidationUtils.notNull(integer, "integer cannot be null"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("integer cannot be null");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void notBlank__shouldThrowIllegalArgumentExceptionWithCustomMessage_whenObjectIsNullOrEmpty(String text) {
        assertThatThrownBy(() -> ValidationUtils.notBlank(text, "text cannot be null or blank"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("text cannot be null or blank");
    }

    @Test
    void notBlank__shouldThrowIllegalArgumentExceptionWithCustomMessage_whenPredicateIsFalse() {
        assertThatThrownBy(() -> ValidationUtils.isTrue(false, "custom message"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("custom message");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isURL__shouldThrowIllegalArgumentExceptionWithCustomMessage_whenUrlIsNullOrEmpty(String url) {
        assertThatThrownBy(() -> ValidationUtils.isURL(url, "custom message"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("custom message");
    }

    @ParameterizedTest
    @ValueSource(strings = {"content", "http://", "www.test", "test.com"})
    void isURL__shouldThrowIllegalArgumentExceptionWithCustomMessage_whenUrlIsInvalid(String url) {
        assertThatThrownBy(() -> ValidationUtils.isURL(url, "custom message"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("custom message");
    }

}