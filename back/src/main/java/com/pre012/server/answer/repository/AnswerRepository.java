package com.pre012.server.answer.repository;

import com.pre012.server.answer.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Modifying
    @Query("delete from Answer a where a.question.id =:questionId")
    public void deleteByQuestionId(@Param("questionId") Long questionId);

    Page<Answer> findByQuestion_Id(@Param("questionId") Long questionId, Pageable pageable);
}
