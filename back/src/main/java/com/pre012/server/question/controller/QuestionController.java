package com.pre012.server.question.controller;

import com.pre012.server.common.dto.MultiObjectResponseDto;
import com.pre012.server.common.dto.MultiResponseDto;
import com.pre012.server.common.dto.SingleResponseDto;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.service.MemberService;
import com.pre012.server.question.dto.QuestionDto;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.mapper.QuestionCommentMapper;
import com.pre012.server.question.mapper.QuestionMapper;
import com.pre012.server.question.service.QuestionService;
import com.pre012.server.question.service.QuestionTagService;
import com.pre012.server.tag.entity.Tag;
import com.pre012.server.tag.mapper.TagMapper;
import com.pre012.server.tag.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/questions")
@Validated
@CrossOrigin("*")
public class QuestionController {
    private final QuestionService questionService;
    private final MemberService memberService;
    private final TagService tagService;
    private final QuestionTagService questionTagService;

    private final QuestionMapper mapper;
    private final QuestionCommentMapper commentMapper;
    private final TagMapper tagMapper;

    public QuestionController(QuestionService questionService, MemberService memberService, TagService tagService, QuestionTagService questionTagService, QuestionMapper mapper, QuestionCommentMapper commentMapper, TagMapper tagMapper) {
        this.questionService = questionService;
        this.memberService = memberService;
        this.tagService = tagService;
        this.questionTagService = questionTagService;
        this.mapper = mapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
    }

    /**
     * ?????? ??????
     */
    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Request requestBody) {

        Question question = mapper.questionRequestToQuestion(requestBody);

        // ????????? member ????????? ??????
        addMemberToQuestion(requestBody, question);

        // ????????? List<Tag> ??????
        List<Tag> findTags = tagService.findVerifyTags(
                tagMapper.tagRequestDtosToTags(requestBody.getTags()));

        Question savedQuestion = questionService.createQuestion(question);

        // ??? QuestionTag ???????????? setTag, setQuestion ?????? <- questionId ??? ???????????? question ?????? ??? ?????? ???.
        questionTagService.createQuestionTags(question, findTags);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * ?????? ?????? ( ??????, ??????, ?????? ?????? )
     */
    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") @Positive Long questionId,
                                        @Valid @RequestBody QuestionDto.Request requestBody) {
        Question question = mapper.questionRequestToQuestion(requestBody);
        question.setId(questionId);

        // ????????? member ????????? ??????
        addMemberToQuestion(requestBody, question);

        // ????????? tag ??????
        List<Tag> findTags = tagService.findVerifyTags(
                tagMapper.tagRequestDtosToTags(requestBody.getTags()));

        Question updatedQuestion = questionService.updateQuestion(question);


        questionTagService.updateQuestionTags(question, findTags);

        return new ResponseEntity(HttpStatus.OK);

    }

    /**
     * ?????? ??????
     */
    @DeleteMapping("{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") @Positive Long questionId,
                                        @Positive @RequestParam Long memberId) {
        questionService.deleteQuestion(questionId, memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * ?????? ?????? ?????? ??? ?????????
     */
    @CrossOrigin("*")
    @GetMapping
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                       @RequestParam String sortedBy) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, sortedBy);
        List<Question> questionList = pageQuestions.getContent();

        List<QuestionDto.searchResponse> searchResponses = mapper.questionsToSearchResponses(questionList);

        return new ResponseEntity<>(
                new MultiObjectResponseDto<>(
                        mapper.resultResponses(searchResponses),
                        pageQuestions),
                HttpStatus.OK);
    }


    /**
     * ?????? ?????? ??????
     */
    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@Positive @PathVariable("question-id") Long questionId,
                                      @Positive @RequestParam Long memberId) {
        Question question = questionService.findQuestion(questionId);


        QuestionDto.getResponse response = mapper.questionToGetResponse(question,
                commentMapper.commentToQuestionCommentResponses(question.getComments()),
                questionService.getBookmarked(memberId, questionId),
                questionService.getLikeStatus(memberId, questionId));

        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }


    /**
     * ?????? ?????????
     */
    @PostMapping("/like/{question-id}")
    public ResponseEntity postQuestionLike(@Positive @PathVariable("question-id") Long questionId,
                                           @Positive @RequestParam Long memberId) {

        // ?????? ????????? ??????????????? ??????
        Member findMember = memberService.findVerifiedMember(memberId);

        questionService.switchLike(questionId, findMember);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * ?????? ?????????
     */
    @PostMapping("/unlike/{question-id}")
    public ResponseEntity postQuestionUnlike(@Positive @PathVariable("question-id") Long questionId,
                                             @Positive @RequestParam Long memberId) {

        // ?????? ????????? ??????????????? ??????
        Member findMember = memberService.findVerifiedMember(memberId);

        questionService.switchUnlike(questionId, findMember);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * ?????? ?????????
     */
    @PostMapping("/bookmark/{question-id}")
    public ResponseEntity postBookmark(@Positive @PathVariable("question-id") Long questionId,
                                       @Positive @RequestParam Long memberId) {

        Member findMember = memberService.findVerifiedMember(memberId);

        questionService.switchBookmark(questionId, findMember);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * ?????? ??????
     */
    @GetMapping("/search")
    public ResponseEntity searchQuestions(@RequestParam String keyword, // @NotBlank ??????????
                                          @RequestParam String type,
                                          @Positive @RequestParam int page) {

        Page<Question> pageQuestions = questionService.searchQuestions(page - 1, keyword, type);
        List<Question> questionList = pageQuestions.getContent();

        List<QuestionDto.searchResponse> searchResponses = mapper.questionsToSearchResponses(questionList);

        return new ResponseEntity<>(
                new MultiObjectResponseDto<>(
                        mapper.resultResponses(searchResponses),
                        pageQuestions),
                HttpStatus.OK);

    }

    // ????????? member ????????? Question ??? set ??????.
    private void addMemberToQuestion(QuestionDto.Request requestBody, Question question) {
        Member member = memberService.findVerifiedMember(requestBody.getMemberId());
        question.setMember(member);
    }
}
