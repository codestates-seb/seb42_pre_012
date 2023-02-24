package com.pre012.server.question.repository;

import com.pre012.server.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Unanswered
    Page<Question> findByAnswerCnt(int answerCnt,Pageable pageable);


    /**
     * 질문 검색용 method
     */

    // title & content 키워드로 질문 찾기
    Page<Question> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);

    // memberId 로 찾기
    Page<Question> findByMemberId(Long memberId, Pageable pageable);

    // memberId 가 북마크한 질문 찾기
    Page<Question> findByBookmarksMemberId(Long memberId, Pageable pageable);

    // memberId 가 답변한 질문 찾기
    Page<Question> findByAnswersMemberId(Long memberId, Pageable pageable);

}
