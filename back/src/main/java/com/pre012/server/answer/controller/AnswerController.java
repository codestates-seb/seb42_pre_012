package com.pre012.server.answer.controller;

import com.pre012.server.answer.dto.*;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.answer.mapper.AnswerCommentMapper;
import com.pre012.server.answer.mapper.AnswerMapper;
import com.pre012.server.answer.repository.AnswerCommentRepository;
import com.pre012.server.answer.service.AnswerService;
import com.pre012.server.common.dto.MultiObjectResponseDto;
import com.pre012.server.common.dto.MultiResponseDto;
import com.pre012.server.member.entity.AnswerLike;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.service.QuestionService;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerMapper answerMapper;
    private final AnswerService answerService;
    private final AnswerCommentMapper answerCommentMapper;
    private final QuestionService questionService;

    public AnswerController(AnswerMapper answerMapper, AnswerService answerService, AnswerCommentMapper answerCommentMapper,QuestionService questionService) {
        this.answerMapper = answerMapper;
        this.answerService = answerService;
        this.answerCommentMapper = answerCommentMapper;
        this.questionService = questionService;
    }

    //질문에 따른 답변 작성
    @PostMapping("/{question-id}")
    public ResponseEntity postAnswer(@PathVariable("question-id") @Positive Long questionId,
                                     @RequestBody AnswerPostDto requestbody){
        Answer answer = answerMapper.answerPostDtoToAnswer(requestbody);
        answer = answerService.createAnswer(answer,questionId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //질문에 해당하는 특정 답변 업데이트
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive Long answerId,
                                      @RequestBody AnswerPatchDto requestbody){
        requestbody.setAnswerId(answerId);

        Answer answer = answerMapper.answerPatchDtoToAnswer(requestbody);
        answer = answerService.updateAnswer(answer,answer.getMember().getId());
        /*
         1. question이 있는지?
         2. answer이 있는지?
         -> 서비스 클래스 내에서 해결 완
         */
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //질문에 해당하는 답변 가져오기
    /*
    response에 member 정보가 하나만 포함되서 정은님 코드 참고해서 comment에는 따로 responseDto에 필드 추가해서 멤버관련 정보 실어두었고,
    api 요청서 response에 member 부분은 answer 멤버 정보라고 생각하여 따로 빼뒀습니다. likeStatus는 제가 그때 바로 말씀드렸어야했는데, 로그인한 멤버의 liketype이 필요하다면,
    request로 멤버id 따로 받아서 새로 추가하겠습니다. 너무 두서없이 코드 작성하고 pr 던지는 것 같아 정말 죄송할따름입니다. 일요일 저녁쯤 메세지 볼 수 있으면 바로 보고 답장하도록 할께요!
     */
    @GetMapping("/{question-id}")
    public ResponseEntity getAnswer(@PathVariable("question-id") @Positive Long questionId,
                                    @RequestParam @Positive Long memberId,
                                    @RequestParam @Positive int page,
                                    @RequestParam (required = false, defaultValue="likeCnt") String sortedBy){

        Page<Answer> pageAnswer = answerService.findAnswers(questionId,page-1,sortedBy);
        List<Answer> answers = pageAnswer.getContent();
        return new ResponseEntity<>(new MultiObjectResponseDto<>((answerMapper.answerMultiToAnswerGetResponseDto(answerMapper.answersToAnswerMultiResponseDtos(answers,memberId))),pageAnswer),HttpStatus.OK);
    }
    //질문에 해당하는 특정 답변 삭제
    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id")@Positive Long answerId,
                                       @RequestParam @Positive Long memberId){

        answerService.deleteAnswer(answerId,memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    답변 post, patch, delete
     */
    @PostMapping("/comments/{answer-id}")
    public ResponseEntity postAnswerComment(@PathVariable("answer-id")@Positive Long answer_id,
                                             @RequestBody AnswerCommentPostDto requestbody){
        AnswerComment answerComment = answerCommentMapper.answerCommentPostDtoToAnswerComment(requestbody);
        answerComment=answerService.createAnswerComment(answerComment,answer_id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/comments/{comment-id}")
    public ResponseEntity patchAnswerComment(@PathVariable("comment-id")@Positive Long commentId,
                                             @RequestBody AnswerCommentPatchDto requestbody){
        requestbody.setCommentId(commentId);

        AnswerComment answerComment = answerCommentMapper.answerCommentPatchDtoToAnswerComment(requestbody);
        answerComment= answerService.updateAnswerComment(answerComment, answerComment.getMember().getId());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity deleteAnswerComment(@PathVariable("comment-id")@Positive Long commentId,
                                              @RequestParam @Positive Long memberId){
        answerService.deleteAnswerComment(commentId,memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*
    이후 추가될 post는 좋아요 증감 관련
     */

    @PostMapping("/like/{answer-id}")
    public ResponseEntity like(@PathVariable("answer-id") @Positive Long answerId,
                               @RequestParam @Positive Long memberId){
        Answer answer = answerService.findVerifiedAnswer(answerId);
        answerService.like(answerId,memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/unlike/{answer-id}")
    public ResponseEntity unLike(@PathVariable("answer-id") @Positive Long answerId,
                                 @RequestParam @Positive Long memberId){
        Answer answer = answerService.findVerifiedAnswer(answerId);
        answerService.unlike(answerId,memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
