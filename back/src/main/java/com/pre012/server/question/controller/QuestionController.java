package com.pre012.server.question.controller;

import com.pre012.server.common.dto.MultiResponseDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.entity.QuestionLike;
import com.pre012.server.member.service.MemberService;
import com.pre012.server.question.dto.QuestionDto;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.entity.QuestionComment;
import com.pre012.server.question.mapper.QuestionMapper;
import com.pre012.server.question.service.QuestionService;
import com.pre012.server.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final MemberService memberService;
    private final QuestionMapper mapper;

    public QuestionController(QuestionService questionService, MemberService memberService, QuestionMapper mapper) {
        this.questionService = questionService;
        this.memberService = memberService;
        this.mapper = mapper;
    }

    /**
     * 질문 등록
     * 이미지 관련 내용 수정 필요 (DTO 도)
     * 태그 관련 내용 수정 필요
     */
    @PostMapping
    public ResponseEntity postQuestion(@RequestBody QuestionDto.Post requestBody) {
        // tag 조회 -> 있는 거면 그 태그랑 질문이랑 매핑?
        // tagService.findByName(tag) 이런 식으로 가져와서 이 question 의 List<QuestionTag> tags 에 넣기?

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
                                        @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setQuestionId(questionId);
        Question question = mapper.questionPatchToQuestion(requestBody);

        // 검증된 member 찾아서 넣기
        Member member = memberService.findVerifiedMember(requestBody.getMemberId());
        question.setMember(member);

        Question updatedQuestion = questionService.updateQuestion(question);

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

        return new ResponseEntity(
                new MultiResponseDto<>(mapper.questionsToQuestionResponses(questions),
                        pageQuestions)
                , HttpStatus.OK);

    }


    /**
     * 질문 상세 조회
     * viewCnt 올려주기 필요
     *
     * @return question / question_comment / member_question_like / question_tag -> tag 정보 줘야하는 거 아닌가?
     */
    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") Long questionId) {
        Question findQuestion = questionService.findVerifyQuestion(questionId);
        int viewCnt = findQuestion.getViewCnt();
        findQuestion.setViewCnt(viewCnt + 1);

        List<QuestionComment> comments = findQuestion.getComments();
        List<QuestionLike> memberLikes = findQuestion.getMemberLikes();
        List<Tag> tags = findQuestion.getTags().stream()
                .map(qt -> qt.getTag())
                .collect(Collectors.toList());

        return new ResponseEntity(HttpStatus.OK);

    }

    /**
     * 질문 좋아요 (완성)
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
     * 질문 싫어요 (완성)
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
     * 질문 북마크 (완성)
     */
    @PostMapping("/bookmark/{question-id}")
    public ResponseEntity postBookmark(@PathVariable("question-id") Long questionId,
                                       @RequestParam Long memberId) {

        Member findMember = memberService.findVerifiedMember(memberId);

        questionService.switchBookmark(questionId, findMember);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 질문 검색
     */
    @GetMapping("/search")
    public ResponseEntity searchQuestions(@RequestParam String keyword,
                                          @RequestParam String type,
                                          @RequestParam int page) {

        Page<Question> questions = questionService.searchQuestions(page - 1, keyword, type);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
