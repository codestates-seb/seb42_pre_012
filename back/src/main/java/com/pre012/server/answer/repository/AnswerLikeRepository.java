package com.pre012.server.answer.repository;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.member.entity.AnswerLike;
import com.pre012.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface AnswerLikeRepository extends JpaRepository<AnswerLike,Long> {
    @Nullable
    @Query("SELECT a FROM member_answer_like a "+
            "WHERE a.member.id = :memberId and a.answer.id = :answerId")
    Optional<AnswerLike> findByMemberAndAnswer(@Param("memberId")Long memberId,
                                               @Param("answerId")Long answerId);
}
