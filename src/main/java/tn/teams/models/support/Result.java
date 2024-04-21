package tn.teams.models.support;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private int totalQuestions = 0;
    private int correctQuestions = 0;

    public void addAnswer(Boolean isCorrect) {
        totalQuestions++;
        if (isCorrect) {
            correctQuestions++;
        }
    }

}
