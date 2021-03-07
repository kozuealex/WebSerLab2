package se.iths.springlab.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JUnitTest {

    @Test
    void callingOneWithValidIdReturnsOneMonkey() {
        MonkeyController monkeyController = new MonkeyController(new TestService());
        var monkey = monkeyController.one(1);

        // assertEquals(1, monkey.getId());
        assertThat(monkey.getId()).isEqualTo(1);
        assertThat(monkey.getName()).isEqualTo("Test Monkey");
        assertThat(monkey.getType()).isEqualTo("Test Type");
        assertThat(monkey.getWeight()).isEqualTo(30);
        assertThat(monkey.getGender()).isEqualTo("Test Gender");
    }

    @Test
    void callingOneWithInvalidIdReturnsException404() {
        MonkeyController monkeyController = new MonkeyController(new TestService());

        var exception = assertThrows(ResponseStatusException.class,
                () -> monkeyController.one(2));
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}