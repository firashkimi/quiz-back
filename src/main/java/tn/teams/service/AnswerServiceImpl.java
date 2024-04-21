package tn.teams.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.teams.exception.ActionRefusedException;
import tn.teams.exception.ResourceUnavailableException;
import tn.teams.exception.UnauthorizedActionException;
import tn.teams.models.Answer;
import tn.teams.models.Question;
import tn.teams.repository.AnswerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Lazy
public class AnswerServiceImpl implements AnswerService {

//    private static final Logger logger = LoggerFactory.getLogger(AnswerServiceImpl.class);
    private final AnswerRepository answerRepository;


    private final QuestionService questionService;





    @Override
    public Answer find(Long id) throws ResourceUnavailableException {
        Optional<Answer> answer = answerRepository.findById(id);

        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new ResourceUnavailableException("Answer with ID " + id + " not found");
        }

    }

    @Override
    public Answer save(Answer answer) throws UnauthorizedActionException {
        return answerRepository.save(answer);
    }

    @Override
    public Answer update(Answer newAnswer) throws ResourceUnavailableException, UnauthorizedActionException {
        Answer currentAnswer = find(newAnswer.getId());

        mergeAnswers(currentAnswer, newAnswer);
        return answerRepository.save(currentAnswer);
    }

    @Override
    public void delete(Answer answer) throws ResourceUnavailableException, UnauthorizedActionException {

        if (questionService.checkIsCorrectAnswer(answer.getQuestion(), answer.getId())) {
            throw new ActionRefusedException("The correct answer can't be deleted");
        }

        answerRepository.delete(answer);
    }

    private void mergeAnswers(Answer currentAnswer, Answer newAnswer) {
        currentAnswer.setText(newAnswer.getText());

        if (newAnswer.getOrder() != null) {
            currentAnswer.setOrder(newAnswer.getOrder());
        }
    }

    @Override
    public List<Answer> findAnswersByQuestion(Question question) {
        return answerRepository.findByQuestionOrderByOrderAsc(question);
    }

    @Override
    public int countAnswersInQuestion(Question question) {
        return answerRepository.countByQuestion(question);
    }

}
