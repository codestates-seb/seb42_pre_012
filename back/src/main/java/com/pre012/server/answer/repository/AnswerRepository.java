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
    Page<Answer> findByQuestion_Id(@Param("questionId") Long questionId, Pageable pageable);

    @Query( value = ""
            + " SELECT DISTINCT question_id "
            + " FROM answer a "
            + " WHERE a.member_id = :memberId "
            , nativeQuery = true
    )
    List<Long> findIdsByMemberId(@Param("memberId")Long memberId);
}
