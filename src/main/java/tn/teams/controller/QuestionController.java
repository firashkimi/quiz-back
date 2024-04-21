package tn.teams.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tn.teams.controller.utils.RestVerifier;
import tn.teams.models.Answer;
import tn.teams.models.Question;
import tn.teams.models.Quiz;
import tn.teams.service.AnswerService;
import tn.teams.service.QuestionService;
import tn.teams.service.QuizService;

import java.util.List;

@RestController
@RequestMapping(QuestionController.ROOT_MAPPING)
@RequiredArgsConstructor
public class QuestionController {

    public static final String ROOT_MAPPING = "/api/questions";

    private final QuestionService questionService;

    private final QuizService quizService;

    private final AnswerService answerService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Question save(Question question, BindingResult result, @RequestParam Long quiz_id) {

        RestVerifier.verifyModelResult(result);

        Quiz quiz = quizService.find(quiz_id);
        question.setQuiz(quiz);

        return questionService.save(question);
    }

    @RequestMapping(value = "/updateAll", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void updateAll(@RequestBody List<Question> questions) {
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            question.setOrders(i + 1);

            questionService.update(question);
        }
    }

    @RequestMapping(value = "/{question_id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Question find(@PathVariable Long question_id) {

        return questionService.find(question_id);
    }

    @RequestMapping(value = "/{question_id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Question update(@PathVariable Long question_id,Question question, BindingResult result) {

        RestVerifier.verifyModelResult(result);

        question.setId(question_id);
        return questionService.update(question);

    }

    @RequestMapping(value = "/{question_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long question_id) {
        Question question = questionService.find(question_id);
        questionService.delete(question);
    }

    @RequestMapping(value = "/{question_id}/answers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Answer> findAnswers(@PathVariable Long question_id) {
        Question question = questionService.find(question_id);
        return answerService.findAnswersByQuestion(question);
    }

    @RequestMapping(value = "/{question_id}/correctAnswer", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Answer getCorrectAnswer(@PathVariable Long question_id) {
        Question question = questionService.find(question_id);
        return questionService.getCorrectAnswer(question);
    }

    @RequestMapping(value = "/{question_id}/correctAnswer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void setCorrectAnswer(@PathVariable Long question_id, @RequestParam Long answer_id) {

        Question question = questionService.find(question_id);
        Answer answer = answerService.find(answer_id);
        questionService.setCorrectAnswer(question, answer);
    }

}
