package com.pre012.server.question.controller;

import com.pre012.server.common.dto.MultiResponseDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.service.MemberService;
import com.pre012.server.question.dto.QuestionCommentDto;
import com.pre012.server.question.dto.QuestionDto;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.mapper.QuestionCommentMapper;
import com.pre012.server.question.mapper.QuestionMapper;
import com.pre012.server.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final MemberService memberService;
    private final QuestionMapper mapper;
    private final QuestionCommentMapper commentMapper;

    public QuestionController(QuestionService questionService, MemberService memberService, QuestionMapper mapper, QuestionCommentMapper commentMapper) {
        this.questionService = questionService;
        this.memberService = memberService;
        this.mapper = mapper;
        this.commentMapper = commentMapper;
    }

    /**
     * 질문 등록
     * 이미지 관련 내용 수정 필요
     * 태그 관련 내용 수정 필요
     */
    @PostMapping
    public ResponseEntity postQuestion(@RequestBody QuestionDto.Request requestBody) {

        Question question = mapper.questionPostToQuestion(requestBody);

        // 검증된 member 찾아서 넣기
        Member member = memberService.findVerifiedMember(requestBody.getMemberId());
        question.setMember(member);

        Question savedQuestion = questionService.createQuestion(question);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 질문 수정 ( 제목, 본문, 태그 수정 )
     * 태그 관련 내용 수정 필요
     */
    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") Long questionId,
                                        @RequestBody QuestionDto.Request requestBody) {
        Question question = mapper.questionPostToQuestion(requestBody);
        question.setId(questionId);

        // 검증된 member 찾아서 넣기
        Member member = memberService.findVerifiedMember(requestBody.getMemberId());

        Question updatedQuestion = questionService.updateQuestion(question, member);

        return new ResponseEntity(HttpStatus.OK);

    }

    /**
     * 질문 삭제
     * answer 지우는 메소드 추가하기
     */
    @DeleteMapping("{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") Long questionId,
                                         @RequestParam Long memberId) {
        questionService.deleteQuestion(questionId, memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 질문 목록 조회 및 필터링
     */
    @GetMapping
    public ResponseEntity getQuestions(@RequestParam int page,
                                       @RequestParam String sortedBy) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, sortedBy);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        mapper.questionsToSearchResponses(questions),
                        pageQuestions),
                HttpStatus.OK);
    }


    /**
     * 질문 상세 조회
     */
    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") Long questionId,
                                      @RequestParam Long memberId) {
        Question question = questionService.findQuestion(questionId);

        List<QuestionCommentDto.Response> commentResponses = commentMapper.commentToQuestionCommentResponses(question.getComments());


        QuestionDto.getResponse response = mapper.questionToGetResponse(question,
                commentResponses,
                questionService.getBookmarked(memberId, questionId),
                questionService.getLikeStatus(memberId, questionId));

        return new ResponseEntity(response, HttpStatus.OK);
    }


    /**
     * 질문 좋아요
     */
    @PostMapping("/like/{question-id}")
    public ResponseEntity postQuestionLike(@PathVariable("question-id") Long questionId,
                                           @RequestParam Long memberId) {

        // 멤버 찾아서 파라미터로 넣기
        Member findMember = memberService.findVerifiedMember(memberId);

        questionService.switchLike(questionId, findMember);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 질문 싫어요
     */
    @PostMapping("/unlike/{question-id}")
    public ResponseEntity postQuestionUnlike(@PathVariable("question-id") Long questionId,
                                             @RequestParam Long memberId) {

        // 멤버 찾아서 파라미터로 넣기
        Member findMember = memberService.findVerifiedMember(memberId);

        questionService.switchUnlike(questionId, findMember);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 질문 북마크
     */
    @PostMapping("/bookmark/{question-id}")
    public ResponseEntity postBookmark(@PathVariable("question-id") Long questionId,
                                       @RequestParam Long memberId) {

        Member findMember = memberService.findVerifiedMember(memberId);

        questionService.switchBookmark(questionId, findMember);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 질문 키워드로 검색
     */
    @GetMapping("/search")
    public ResponseEntity searchQuestions(@RequestParam String keyword,
                                          @RequestParam String type,
                                          @RequestParam int page) {

        Page<Question> pageQuestions = questionService.searchQuestions(page - 1, keyword, type);
        List<Question> questions = pageQuestions.getContent();



        return new ResponseEntity<>(
                new MultiResponseDto<>(
                    mapper.questionsToSearchResponses(questions),
                    pageQuestions),
                HttpStatus.OK);

    }
}
