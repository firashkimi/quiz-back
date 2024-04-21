package tn.teams.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.teams.models.Question;
import tn.teams.models.Quiz;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    int countByQuiz(Quiz quiz);

    int countByQuizAndIsValidTrue(Quiz quiz);

//    List<Question> findByQuizOrdersByOrdersAsc(Quiz quiz);

//    List<Question> findByQuizAndIsValidTrueOrdersByOrderAsc(Quiz quiz);
}