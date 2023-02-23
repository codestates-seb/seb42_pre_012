package com.pre012.server.answer.repository;

import com.pre012.server.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query(value = "SELECT p FROM  p WHERE p.question.id = :question_id")
    List<Answer> findAllByQuestionIdQuery(@Param("question_id") Long question_id);
}
