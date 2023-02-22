package com.pre012.server.question.repository;

import com.pre012.server.question.entity.Question;
import com.pre012.server.question.entity.QuestionTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Unanswered
    @Query("select q " +
            "from Question q left join Answer a on q.question_id = a.question_id " +
            "where a.answer_id is null")
    Page<Question> findUnanswered(Pageable pageable);

    @Query
    Page<Question> findHot(Pageable pageable);

    @Query
    int countAnswer();


}
