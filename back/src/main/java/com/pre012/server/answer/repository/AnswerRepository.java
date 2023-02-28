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
    /**
    정은님께서 questionid로 delete하게 해달라고 하셔서 쿼리를 짜긴했는데 jpql에 미숙해서 잘 작동하는진 모르겠습니다.
    테스트해보시고 작동 안되시면 바로 말씀주세요! 일요일 일정이 좀 있어서 저녁에라도 알아보고 수정해서 다시 pr 올릴께요!
     */
    @Modifying
    @Query("delete from Answer a where a.question.id =:questionId")
    void deleteByQuestionId(@Param("questionId") Long questionId);

    Page<Answer> findByQuestion_Id(@Param("questionId") Long questionId, Pageable pageable);
}
