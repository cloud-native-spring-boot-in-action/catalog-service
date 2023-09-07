package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

@JsonTest
class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws IOException {
        var book = Book.of("1234567890", "Java", "Nilvandro", 89.4);
        var jsonContent = json.write(book);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());
    }

    @Test
    void deserialize() throws IOException {
        var content = """
                    {
                        "isbn": "1234567890",
                        "title": "Java",
                        "author": "Nino",
                        "price": 10.0
                    }
                """;

        Assertions.assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(Book.of("1234567890", "Java", "Nino", 10.0));
    }
}
