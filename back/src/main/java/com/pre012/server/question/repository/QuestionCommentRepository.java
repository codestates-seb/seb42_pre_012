package com.pre012.server.question.repository;

import com.pre012.server.question.entity.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {

}
