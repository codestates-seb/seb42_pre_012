package com.pre012.server.question.controller;

import com.pre012.server.member.entity.Member;
import com.pre012.server.question.dto.QuestionCommentDto;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.entity.QuestionComment;
import com.pre012.server.question.mapper.QuestionCommentMapper;
import com.pre012.server.question.service.QuestionCommentService;
import com.pre012.server.question.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions/comments")
public class QuestionCommentController {

    // DI
    private final QuestionCommentService questionCommentService;
    private final QuestionService questionService;
    private final MemberService memberService;
    private final QuestionCommentMapper mapper;

    public QuestionCommentController(QuestionCommentService questionCommentService, QuestionService questionService, MemberService memberService, QuestionCommentMapper mapper) {
        this.questionCommentService = questionCommentService;
        this.questionService = questionService;
        this.memberService = memberService;
        this.mapper = mapper;
    }

    /**
     * 댓글 등록
     */

    @PostMapping("/{question-id}")
    public ResponseEntity postQuestionComment(@PathVariable("question-id") Long questionId,
                                              @RequestBody QuestionCommentDto.Post requestBody) {

        QuestionComment questionComment = mapper.questionCommentPostToQuestionComment(requestBody);

        // 입력 받은 questionId 로 question 찾아서 questionComment 에 넣음
        Question findQuestion = questionService.findVerifyQuestion(questionId);
        questionComment.setQuestion(findQuestion);

        // member 검증 후 넣어주기
        Member findMember = memberService.findVerifyMember(requestBody.getMemberId());
        questionComment.setMember(findMember);


        questionCommentService.createComment(questionComment);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 댓글 수정
     */
    @PatchMapping("/{comment-id}")
    public ResponseEntity patchQuestionComment(@PathVariable("comment-id") Long commentId,
                                               @RequestBody QuestionCommentDto.Patch requestBody) {
        requestBody.setCommentId(commentId);
        QuestionComment questionComment = mapper.questionCommentPatchToQuestionComment(requestBody);

        questionCommentService.updateComment(questionComment);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteQuestionComment(@PathVariable("comment-id") Long commentId,
                                                @RequestParam Long memberId) {

        questionCommentService.deleteComment(commentId, memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
