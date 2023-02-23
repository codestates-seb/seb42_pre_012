package com.pre012.server.answer.controller;

import com.pre012.server.answer.dto.*;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.answer.enums.sortedBy;
import com.pre012.server.answer.mapper.AnswerCommentMapper;
import com.pre012.server.answer.mapper.AnswerMapper;
import com.pre012.server.answer.repository.AnswerCommentRepository;
import com.pre012.server.answer.service.AnswerService;
import com.pre012.server.member.entity.AnswerLike;
import com.pre012.server.question.entity.Question;
import org.hibernate.validator.constraints.ParameterScriptAssert;
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

    public AnswerController(AnswerMapper answerMapper, AnswerService answerService, AnswerCommentMapper answerCommentMapper) {
        this.answerMapper = answerMapper;
        this.answerService = answerService;
        this.answerCommentMapper = answerCommentMapper;
    }

    //질문에 따른 답변 작성
    @PostMapping("/{question-id}")
    public ResponseEntity postAnswer(@PathVariable("question-id") @Positive Long question_id,
                                     @RequestBody AnswerPostDto requestbody){
        Answer answer = answerMapper.answerPostDtoToAnswer(requestbody);
        answer = answerService.createAnswer(answer,question_id);
        /*질문 서비스 구현시 추가예정
        / why?
        -> 생성한 answer을 question이 가지고 있는 list<answer>에 포함시킴

        멤버에는 포함시키지 않는 이유?
        -> 멤버를 통해 답변만 볼일이 없음. ex) 멤버 프로필에서 답변만 조회하는 경우
           우린 멤버를 통해 질문글만 조회하기로 합의.

        Question question = questionService.~(question_id)
        question.addAnswer(answer);
        */

        return new ResponseEntity<>(answerMapper.answerToAnswerResponseDto(answer), HttpStatus.CREATED);
//        AnswerResponseDto response = new AnswerResponseDto(1L,1L,1L,"안녕하세요!",0);
//        return ResponseEntity.ok(response);
    }

    //숫자만 열거하면 이상하니 구분용 엔드포인트를 추가하면 어떨지?
    //질문에 해당하는 특정 답변 업데이트
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive Long answer_id,
                                      @RequestBody AnswerPatchDto requestbody){
        requestbody.setAnswer_id(answer_id);

        Answer answer = answerMapper.answerPatchDtoToAnswer(requestbody);
        answerService.updateAnswer(answer,answer.getMember().getId());
        /*
         1. question이 있는지?
         2. answer이 있는지?
         -> 서비스 클래스 내에서 해결 완
         */

        return new ResponseEntity<>(answerMapper.answerToAnswerResponseDto(answer), HttpStatus.OK);
    }
    //질문에 해당하는 답변 가져오기
    @GetMapping("/{question_id}")
    public ResponseEntity getAnswer(@PathVariable("question_id") @Positive Long question_id,
                                    @RequestParam @Positive int page,
                                    @RequestParam (required = false, defaultValue="likeCnt") String sortedBy){
        //페이지네이션 처리할지?
        /*Question 서비스<- question_id를 통해
         Question question = 값 추출
         question이 가진 List<Answer>을 가져옴
         이후 Answer이 가진 List<AnswerComment>가져옴
         */

        return ResponseEntity.ok(null);
    }
    //질문에 해당하는 특정 답변 삭제
    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer_id")@Positive Long answer_id,
                                       @RequestParam @Positive Long member_id){
        //question 하나당 아이디가 생기는지 아니면 그냥 answer id가 question과 상관없이 중복으로 생기는지?
        answerService.deleteAnswer(answer_id,member_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    답변코멘트 관리하는 컨트롤러도 구현 요망 (post,patch,delete)
     */
    @PostMapping("/comments/{answer-id}")
    public ResponseEntity postAnswerComment(@PathVariable("answer-id")@Positive Long answer_id,
                                             @RequestBody AnswerCommentPostDto requestbody){
        AnswerComment answerComment = answerCommentMapper.answerCommentPostDtoToAnswerComment(requestbody);
        answerComment=answerService.createAnswerComment(answerComment,answer_id);

        return new ResponseEntity<>(answerCommentMapper.answerCommentToAnswerCommentResponseDto(answerComment),HttpStatus.OK);
    }

    @PatchMapping("/comments/{comment-id}")
    public ResponseEntity patchAnswerComment(@PathVariable("comment-id")@Positive Long comment_id,
                                             @RequestBody AnswerCommentPatchDto requestbody){
        requestbody.setComment_id(comment_id);

        AnswerComment answerComment = answerCommentMapper.answerCommentPatchDtoToAnswerComment(requestbody);
        answerService.updateAnswerComment(answerComment, answerComment.getMember().getId());

        return new ResponseEntity<>(answerCommentMapper.answerCommentToAnswerCommentResponseDto(answerComment), HttpStatus.OK);

    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity deleteAnswerComment(@PathVariable("comment-id")@Positive Long comment_id,
                                              @RequestParam @Positive Long member_id){
        answerService.deleteAnswerComment(comment_id,member_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*
    이후 추가될 post는 좋아요 증감 관련
     */

    @PostMapping("/like/{answer_id}/")
    public ResponseEntity like(@PathVariable("answer_id") @Positive Long answer_id,
                               @RequestParam @Positive Long member_id){
        Answer answer = answerService.findVerifiedAnswer(answer_id);
        answerService.like(answer_id,member_id);
        return new ResponseEntity<>(answer.getLikeCnt(),HttpStatus.OK);
    }

    @PostMapping("/unlike/{answer_id}/")
    public ResponseEntity unLike(@PathVariable("answer_id") @Positive Long answer_id,
                                 @RequestParam @Positive Long member_id){
        Answer answer = answerService.findVerifiedAnswer(answer_id);
        answerService.unlike(answer_id,member_id);
        return new ResponseEntity<>(answer.getLikeCnt(),HttpStatus.OK);
    }
}
