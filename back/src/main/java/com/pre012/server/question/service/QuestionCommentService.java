package com.pre012.server.question.service;

import com.pre012.server.exception.BusinessLogicException;
import com.pre012.server.exception.ExceptionCode;
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
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        findComment.setContent(questionComment.getContent());
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Long commentId, Long memberId) {
        QuestionComment findComment = findVerifyComment(commentId);
        if (!findComment.getMember().getId().equals(memberId)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        repository.deleteById(commentId);
    }


    private QuestionComment findVerifyComment(Long questionCommentId) {
        Optional<QuestionComment> optionalComment = repository.findById(questionCommentId);

        QuestionComment findComment = optionalComment
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_TAG_NOT_FOUND));

        return findComment;
    }

}
