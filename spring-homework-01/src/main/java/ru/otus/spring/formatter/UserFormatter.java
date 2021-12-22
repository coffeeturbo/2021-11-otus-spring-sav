package ru.otus.spring.formatter;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.User;

@Component
public class UserFormatter {
    public String format(final User user) {
        return user.getName() + " " + user.getSurname();
    }
}
