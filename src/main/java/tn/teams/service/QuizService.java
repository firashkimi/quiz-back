
package tn.teams.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.teams.exception.ResourceUnavailableException;
import tn.teams.exception.UnauthorizedActionException;
import tn.teams.models.Quiz;
import tn.teams.models.support.Response;
import tn.teams.models.support.Result;

import java.util.List;

public interface QuizService {
    Quiz save(Quiz quiz);

    Page<Quiz> findAll(Pageable pageable);

    Page<Quiz> findAllPublished(Pageable pageable);



    Quiz find(Long id) throws ResourceUnavailableException;

    Quiz update(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

    void delete(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

    Page<Quiz> search(String query, Pageable pageable);

    Result checkAnswers(Quiz quiz, List<Response> answersBundle);

    void publishQuiz(Quiz quiz);
}
