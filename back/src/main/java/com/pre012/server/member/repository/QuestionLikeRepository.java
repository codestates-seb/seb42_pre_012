package com.pre012.server.member.repository;

import com.pre012.server.member.entity.QuestionLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionLikeRepository extends JpaRepository<QuestionLike, Long> {

    Optional<QuestionLike> findByMemberIdAndQuestionId(Long memberId, Long questionId);
}
