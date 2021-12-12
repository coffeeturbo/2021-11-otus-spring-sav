package ru.otus.spring.domain.formatter;

import lombok.Value;
import ru.otus.spring.domain.User;

@Value
public class UserFormatter {
    User user;

    public String format() {
        return user.getName() + " " + user.getSurname();
    }
}
