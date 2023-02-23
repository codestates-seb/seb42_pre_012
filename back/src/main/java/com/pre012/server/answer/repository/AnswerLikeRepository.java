package com.pre012.server.answer.repository;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.member.entity.AnswerLike;
import com.pre012.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface AnswerLikeRepository extends JpaRepository<AnswerLike,Long> {
    @Nullable
    Optional<AnswerLike> findByMemberAndAnswer(Member member, Answer answer);
}
