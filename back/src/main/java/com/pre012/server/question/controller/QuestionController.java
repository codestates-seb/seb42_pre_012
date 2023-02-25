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
import com.pre012.server.tag.entity.Tag;
import com.pre012.server.tag.mapper.TagMapper;
import com.pre012.server.tag.service.TagService;
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
    private final TagService tagService;

    private final QuestionMapper mapper;
    private final QuestionCommentMapper commentMapper;
    private final TagMapper tagMapper;

    public QuestionController(QuestionService questionService, MemberService memberService, TagService tagService, QuestionMapper mapper, QuestionCommentMapper commentMapper, TagMapper tagMapper) {
        this.questionService = questionService;
        this.memberService = memberService;
        this.tagService = tagService;
        this.mapper = mapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
    }

    /**
     * 질문 등록
     */
    @PostMapping
    public ResponseEntity postQuestion(@RequestBody QuestionDto.Request requestBody) {

        Question question = mapper.questionRequestToQuestion(requestBody);

        // 검증된 member 찾아서 넣기
        addMemberToQuestion(requestBody, question);

        Question savedQuestion = questionService.createQuestion(question);

        // List<QuestionTag> 넣기 <- questionId 가 필요해서 question 생성 후 해야 함.
        List<Tag> findTags = tagService.findVerifyTags(
                tagMapper.tagRequestDtosToTagList(requestBody.getTags()));
        questionService.addQuestionTagsToQuestion(question, findTags);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 질문 수정 ( 제목, 본문, 태그 수정 )
     * 태그 관련 내용 수정 필요
     */
    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") Long questionId,
                                        @RequestBody QuestionDto.Request requestBody) {
        Question question = mapper.questionRequestToQuestion(requestBody);
        question.setId(questionId);

        // 검증된 member 찾아서 넣기
        addMemberToQuestion(requestBody,question);

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


        QuestionDto.getResponse response = mapper.questionToGetResponse(question,
                commentMapper.commentToQuestionCommentResponses(question.getComments()),
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
     * 질문 검색
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

    // 검증된 member 찾아서 Question 에 set 하기.
    private void addMemberToQuestion(QuestionDto.Request requestBody, Question question) {
        Member member = memberService.findVerifiedMember(requestBody.getMemberId());
        question.setMember(member);
    }
}
