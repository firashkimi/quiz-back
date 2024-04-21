package tn.teams.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.teams.models.Answer;
import tn.teams.models.Question;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    int countByQuestion(Question question);

    List<Answer> findByQuestionOrderByOrderAsc(Question question);

}
