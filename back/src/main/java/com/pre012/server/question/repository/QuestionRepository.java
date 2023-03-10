package com.pre012.server.question.repository;

import com.pre012.server.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * 질문 목록 조회 및 필터링 - Unanswered
     */
    Page<Question> findByAnswerCnt(int answerCnt, Pageable pageable);


    /**
     * 질문 검색용 method
     */

    // title & content 키워드로 질문 찾기
    Page<Question> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);

    // tag 로 질문 찾기에 사용
    Page<Question> findByIdIn(List<Long> Ids, Pageable pageable);

    // memberId 로 찾기
    Page<Question> findByMemberId(Long memberId, Pageable pageable);

    // memberId 가 북마크한 질문 찾기
    Page<Question> findByBookmarksMemberId(Long memberId, Pageable pageable);

    // memberId 가 답변한 질문 찾기
    Page<Question> findByAnswersMemberId(Long memberId, Pageable pageable);

}
