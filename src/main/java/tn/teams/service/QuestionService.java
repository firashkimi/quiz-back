package tn.teams.service;

import tn.teams.exception.ResourceUnavailableException;
import tn.teams.exception.UnauthorizedActionException;
import tn.teams.models.Answer;
import tn.teams.models.Question;
import tn.teams.models.Quiz;

import java.util.List;

public interface QuestionService {
    Question save(Question question) throws UnauthorizedActionException;

    Question find(Long id) throws ResourceUnavailableException;

//    List<Question> findQuestionsByQuiz(Quiz quiz);
//
//    List<Question> findValidQuestionsByQuiz(Quiz quiz);

    Question update(Question question) throws ResourceUnavailableException, UnauthorizedActionException;

    void delete(Question question) throws ResourceUnavailableException, UnauthorizedActionException;

    Boolean checkIsCorrectAnswer(Question question, Long answer_id);

    void setCorrectAnswer(Question question, Answer answer);

    Answer getCorrectAnswer(Question question);

    Answer addAnswerToQuestion(Answer answer, Question question);

    int countQuestionsInQuiz(Quiz quiz);

    int countValidQuestionsInQuiz(Quiz quiz);
}
