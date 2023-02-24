package com.pre012.server.question.service;

import com.pre012.server.member.entity.Member;
import com.pre012.server.question.entity.QuestionComment;
import com.pre012.server.question.repository.QuestionCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class QuestionCommentService {

    private final QuestionCommentRepository repository;

    public QuestionCommentService(QuestionCommentRepository repository) {
        this.repository = repository;
    }

    /**
     * 댓글 생성(등록)
     */
    public void createComment(QuestionComment questionComment) {
        repository.save(questionComment);
    }

    /**
     * 댓글 수정
     */
    public void updateComment(QuestionComment questionComment, Member member) {
        QuestionComment findComment = findVerifyComment(questionComment.getId());

        if (findComment.getMember().getId() != member.getId()) {
            throw new RuntimeException("작성자가 아닌 사람이 댓글 수정하려고 함");
        }

        findComment.setContent(questionComment.getContent());

//        repository.save(findComment); save 안해도 저장됨.
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Long commentId, Long memberId) {
        QuestionComment findComment = findVerifyComment(commentId);
        if (!findComment.getMember().getId().equals(memberId)) {
            throw new RuntimeException("다른 멤버가 지우려고 함"); // 비즈니스 예외로 수정 필요
        }

        repository.deleteById(commentId);
    }



    private QuestionComment findVerifyComment(Long questionCommentId) {
        Optional<QuestionComment> optionalComment = repository.findById(questionCommentId);

        QuestionComment findComment = optionalComment
                .orElseThrow(() -> new RuntimeException("잘못된 코맨트")); // 비즈니스 예외 수정

        return findComment;
    }

}
