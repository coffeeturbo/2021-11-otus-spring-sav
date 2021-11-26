package domain;

import lombok.Value;

import java.util.List;

@Value
public class Question {
    List<String> question;

    public String getQuestionText() {
        return question.get(0);
    }

    public String getAnswerVariantsText() {
        return String.join(" ", question.subList(1, question.size()));
    }

    public String getQuestionWithVariants() {
        return String.join(" ", question);
    }
}
