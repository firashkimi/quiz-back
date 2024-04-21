package tn.teams.service;

import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.teams.exception.ActionRefusedException;
import tn.teams.exception.InvalidParametersException;
import tn.teams.exception.ResourceUnavailableException;
import tn.teams.exception.UnauthorizedActionException;
import tn.teams.models.Question;
import tn.teams.models.Quiz;
import tn.teams.models.support.Response;
import tn.teams.models.support.Result;
import tn.teams.repository.QuizRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Lazy
public class QuizServiceImpl implements QuizService {

//    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);
    private final QuizRepository quizRepository;

    private final QuestionService questionService;


    @Override
    public Quiz save(Quiz quiz) {

        return quizRepository.save(quiz);
    }

    @Override
    public Page<Quiz> findAll(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }

    @Override
    public Page<Quiz> findAllPublished(Pageable pageable) {
        return quizRepository.findByIsPublishedTrue(pageable);
    }

    @Override
    public Quiz find(Long id) throws ResourceUnavailableException {
        Optional<Quiz> quiz = quizRepository.findById(id);

        if (quiz.isPresent()) {
            return quiz.get();
        } else {
            throw new ResourceUnavailableException("Quiz " + id + " not found");
        }

    }

    @Override
    public Quiz update(Quiz newQuiz) throws UnauthorizedActionException, ResourceUnavailableException {
        Quiz currentQuiz = find(newQuiz.getId());

        mergeQuizzes(currentQuiz, newQuiz);
        return quizRepository.save(currentQuiz);
    }

    @Override
    public void delete(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException {
        quizRepository.delete(quiz);
    }

    private void mergeQuizzes(Quiz currentQuiz, Quiz newQuiz) {
        currentQuiz.setName(newQuiz.getName());
        currentQuiz.setDescription(newQuiz.getDescription());
    }

    @Override
    public Page<Quiz> search(String query, Pageable pageable) {
        return quizRepository.searchByName(query, pageable);
    }


    @Override
    public Result checkAnswers(Quiz quiz, List<Response> answersBundle) {
        Result results = new Result();

        for (Question question : quiz.getQuestions()) {
            boolean isFound = false;

            if (!question.getIsValid()) {
                continue;
            }

            for (Response bundle : answersBundle) {
                if (bundle.getQuestion().equals(question.getId())) {
                    isFound = true;
                    results.addAnswer(questionService.checkIsCorrectAnswer(question, bundle.getSelectedAnswer()));
                    break;
                }
            }

            if (!isFound) {
                throw new InvalidParametersException("No answer found for question: " + question.getText());
            }
        }

        return results;
    }

    @Override
    public void publishQuiz(Quiz quiz) {
        int count = questionService.countValidQuestionsInQuiz(quiz);

        if (count > 0) {
            quiz.setIsPublished(true);
            quizRepository.save(quiz);
        } else {
            throw new ActionRefusedException("The quiz doesn't have any valid questions");
        }
    }

}