package com.pre012.server.question.service;

import com.pre012.server.member.entity.Member;
import com.pre012.server.member.entity.QuestionLike;
import com.pre012.server.member.enums.LikeType;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * 질문 생성 (등록)
     */
    public Question createQuestion(Question question) {

        Question savedQuestion = questionRepository.save(question);

        return savedQuestion;
    }

    /**
     * 질문 수정
     *
     */
    public Question updateQuestion(Question question) {
        // 존재하는 question 인지 확인
        Question findQuestion = findVerifyQuestion(question.getId());

        // 다 들어오는 거면 그냥 다 set 으로 해주면 될 듯?
        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getContent())
                .ifPresent(content -> findQuestion.setContent(content));
        Optional.ofNullable(question.getTags())
                .ifPresent(questionTags ->
                        questionTags.forEach(tag -> findQuestion.setTags(tag)));

        return questionRepository.save(findQuestion);

    }

    /**
     * 질문 삭제
     * 답변 삭제 메소드 추가 필요!!!
     */
    public void deleteQuestion(Long questionId, Long memberId) {
        Question findQuestion = findVerifyQuestion(questionId);

        // answer 지우는 메소드 추가 - answerRepository.deleteByQuestionId() 이런 거 만들 수 있나..?

        // question 에 저장된 memberId 와 쿼리스트링으로 받은 memberId 비교
        if (!findQuestion.getMember().getId().equals(memberId)) {
            throw new RuntimeException("다른 아이디가 지우려고 함");  // 비즈니스 예외 넣기
        }

        questionRepository.deleteById(questionId);
    }

    /**
     * 질문 목록 조회
     */
    public Page<Question> findQuestions(int page, String sortedBy) {

        if (sortedBy.equals("Unanswered")) {
            return questionRepository.findUnanswered(PageRequest.of(page, 15));
        }
        else if (sortedBy.equals("Interesting")) { // view 많은 순
            return questionRepository.findAll(PageRequest.of(page, 15,
                    Sort.by("viewCnt").descending()));
        }
        else if (sortedBy.equals("Hot")) { // Answer 많은 순
            return questionRepository.findAll(PageRequest.of(page, 15,
                    Sort.by("answerCnt").descending()));
        }

        // Default : Newest
        return questionRepository.findAll(PageRequest.of(page, 15,
            Sort.by("createdAt").descending()));

    }

    /**
     * 질문 상세 조회
     */
//    public Question findQuestion(Long questionId) {
//        Question findQuestion = findVerifyQuestion(questionId);
//
//        // 코맨트랑 태그, 좋아요까지 같이 다 보내야 할 것 같은데...............
//
//    }

    /**
     * 질문 좋아요
     */
    public void createLike(Long questionId, Member member) {
        Question findQuestion = findVerifyQuestion(questionId);

        int likeCnt = findQuestion.getLikeCnt();
        findQuestion.setLikeCnt(likeCnt + 1);

        QuestionLike questionLike = new QuestionLike();
        questionLike.setQuestion(findQuestion);
        questionLike.setMember(member);
        questionLike.setLikeType(LikeType.LIKE);

        findQuestion.setMemberLikes(questionLike);

    }

    /**
     * 질문 싫어요
     */
    public void createUnlike(Long questionId, Member member) {
        Question findQuestion = findVerifyQuestion(questionId);

        int likeCnt = findQuestion.getLikeCnt();
        findQuestion.setLikeCnt(likeCnt - 1);

        QuestionLike questionLike = new QuestionLike();
        questionLike.setQuestion(findQuestion);
        questionLike.setMember(member);
        questionLike.setLikeType(LikeType.UNLIKE);

        findQuestion.setMemberLikes(questionLike);

    }



    // 검증된 question 레포에서 찾기
    public Question findVerifyQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion
                .orElseThrow(() -> new RuntimeException("잘못된 커피쓰")); // 비즈니스 예외처리 넣기

        return findQuestion;
    }



}
