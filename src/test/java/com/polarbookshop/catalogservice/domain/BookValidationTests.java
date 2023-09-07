package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = Book.of("1234567892", "Atomic Habits", "James Clear", 20.0, "O'Reilly");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnIsDefinedButIncorrectThenValidationFails() {
        var book = Book.of("12345", "Atomic Habits", "James Clear", 20.0, "O'Reilly");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid.");
    }
}
