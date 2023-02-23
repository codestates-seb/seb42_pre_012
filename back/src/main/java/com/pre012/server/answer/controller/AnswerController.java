package com.pre012.server.answer.controller;

import com.pre012.server.answer.dto.AnswerPostDto;
import com.pre012.server.answer.dto.AnswerResponseDto;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.mapper.AnswerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerMapper answerMapper;

    public AnswerController(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    //질문에 따른 답변 작성을 위해 엔드포인트 추가
    @PostMapping("/{question_id}")
    public ResponseEntity postAnswer(/*@PathVariable("question_id") @Positive Long question_id,
                                     @RequestBody AnswerPostDto requestbody*/){
        /*Answer answer = answerMapper.answerPostDtoToAnswer(requestbody);
        질문 서비스 구현시 추가예정
        Question question = questionService.~(question_id)
        question.addAnswer(answer);
        return new ResponseEntity<>()*/
        AnswerResponseDto response = new AnswerResponseDto(1L,1L,1L,"안녕하세요!",0);
        return ResponseEntity.ok(response);
    }

    //숫자만 열거하면 이상하니 구분용 엔드포인트를 추가하면 어떨지?
    @PatchMapping("/{question_id}/{answer_id}")
    public ResponseEntity patchAnswer(){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{question_id}")
    public ResponseEntity getAnswer(){
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{question_id}/{answer_id}")
    public ResponseEntity deleteAnswer(){
        return ResponseEntity.ok(null);
    }

    /*
    이후 추가될 post는 좋아요 증감 관련
     */

    @PostMapping("/{question_id}/{answer_id}/like")
    public ResponseEntity like(){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{question_id}/{answer_id}/unlike")
    public ResponseEntity unLike(){
        return ResponseEntity.ok(null);
    }
}
