package ru.otus.spring.formatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.User;

class UserFormatterTest {

    @Test
    void format() {
        User user = new User("John", "Doue");
        UserFormatter formatter = new UserFormatter();

        Assertions.assertThat(formatter.format(user))
                .isEqualTo("John Doue");

    }
}