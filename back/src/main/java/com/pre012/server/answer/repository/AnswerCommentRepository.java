package com.pre012.server.answer.repository;

import com.pre012.server.answer.entity.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment,Long> {
}
