
package tn.teams.service;

import tn.teams.exception.ResourceUnavailableException;
import tn.teams.exception.UnauthorizedActionException;
import tn.teams.models.Answer;
import tn.teams.models.Question;

import java.util.List;

public interface AnswerService {
    Answer save(Answer answer) throws UnauthorizedActionException;

    Answer find(Long id) throws ResourceUnavailableException;

    Answer update(Answer newAnswer) throws UnauthorizedActionException, ResourceUnavailableException;

    void delete(Answer answer) throws UnauthorizedActionException, ResourceUnavailableException;

    List<Answer> findAnswersByQuestion(Question question);

    int countAnswersInQuestion(Question question);
}

