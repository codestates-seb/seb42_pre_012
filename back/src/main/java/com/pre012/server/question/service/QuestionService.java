package com.pre012.server.question.service;

import com.pre012.server.member.entity.Bookmark;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.entity.QuestionLike;
import com.pre012.server.member.enums.LikeType;
import com.pre012.server.member.repository.BookmarkRepository;
import com.pre012.server.member.repository.QuestionLikeRepository;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.repository.QuestionRepository;
import com.pre012.server.question.repository.QuestionTagRepository;
import com.pre012.server.tag.entity.Tag;
import com.pre012.server.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionLikeRepository questionLikeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final QuestionTagRepository questionTagRepository;
    private final TagRepository tagRepository;

    public QuestionService(QuestionRepository questionRepository, QuestionLikeRepository questionLikeRepository, BookmarkRepository bookmarkRepository, QuestionTagRepository questionTagRepository, TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.questionLikeRepository = questionLikeRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.questionTagRepository = questionTagRepository;
        this.tagRepository = tagRepository;
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
     */
    public Question updateQuestion(Question question) {
        // DB 에서 Question 가져오기
        Question findQuestion = findVerifyQuestion(question.getId());

        if (findQuestion.getMember().getId() != question.getMember().getId()) {
            throw new RuntimeException("작성자가 아닌 사람이 질문 수정하려고 함");  // 수정 필요 @@@
        }

        findQuestion.setTitle(question.getTitle());
        findQuestion.setContent(question.getContent());

        return findQuestion;

    }

    /**
     * 질문 삭제
     * 답변 삭제 메소드 추가 필요!!!
     */
    public void deleteQuestion(Long questionId, Long memberId) {
        Question findQuestion = findVerifyQuestion(questionId);

        // answer 지우는 메소드 추가 - answerRepository.deleteByQuestionId() 이런거

        // question 에 저장된 memberId 와 쿼리스트링으로 받은 memberId 비교
        if (!findQuestion.getMember().getId().equals(memberId)) {
            throw new RuntimeException("다른 아이디가 지우려고 함");  // 비즈니스 예외 넣기
        }

        questionRepository.deleteById(questionId);
    }

    /**
     * 질문 목록 조회
     * Unanswered : answerCnt == 0
     * Interesting : viewCnt 많은 순
     * Hot : answerCnt 많은 순
     */
    public Page<Question> findQuestions(int page, String sortedBy) {
        // 필터 조건 대문자로 변경
        sortedBy = sortedBy.toUpperCase();

        if (sortedBy.equals("UNANSWERED")) {
            return questionRepository.findByAnswerCnt(0, PageRequest.of(page, 15));

        } else if (sortedBy.equals("INTERESTING")) {
            return questionRepository.findAll(PageRequest.of(page, 15,
                    Sort.by("viewCnt").descending()));

        } else if (sortedBy.equals("HOT")) {
            return questionRepository.findAll(PageRequest.of(page, 15,
                    Sort.by("answerCnt").descending()));

        } else {

            // Default : NEWEST (최신 등록순)
            return questionRepository.findAll(PageRequest.of(page, 15,
                    Sort.by("createdAt").descending()));
        }
    }

    /**
     * 질문 상세 조회
     * viewCnt + 1
     */
    public Question findQuestion(Long questionId) {
        Question findQuestion = findVerifyQuestion(questionId);
        int viewCnt = findQuestion.getViewCnt();
        findQuestion.setViewCnt(viewCnt + 1);

        return findQuestion;
    }

    /**
     * 질문 좋아요 (코드 리팩토링 필요)
     * QuestionLike 가 없으면 새로운 좋아요를 만들어서 주입하고, 좋아요 수 (likeCnt) + 1
     * 있는데 싫어요 상태이면 Like 상태로 바꾸고, 좋아요 수 + 2
     */
    public void switchLike(Long questionId, Member member) {
        Question findQuestion = findVerifyQuestion(questionId);
        int likeCnt = findQuestion.getLikeCnt();

        Optional<QuestionLike> optionalQuestionLike = questionLikeRepository.findByMemberIdAndQuestionId(member.getId(), questionId);

        if (optionalQuestionLike.isPresent()) {
            QuestionLike findQuestionLike = optionalQuestionLike.get();

            if (findQuestionLike.getLikeType().equals(LikeType.UNLIKE)) {
                findQuestionLike.setLikeType(LikeType.LIKE);
                findQuestion.setLikeCnt(likeCnt + 2);
            }
        } else {
            QuestionLike questionLike = new QuestionLike();
            questionLike.setQuestion(findQuestion);
            questionLike.setMember(member);
            questionLike.setLikeType(LikeType.LIKE);

            questionLikeRepository.save(questionLike); // 새로 만든거니까 repo 에 save 해줌

            findQuestion.setLikeCnt(likeCnt + 1);
        }
    }

    /**
     * 질문 싫어요 (코드 리팩토링 필요)
     * QuestionLike 가 없으면 새로운 QuestionLike 만들어서 싫어요 상태로 주입. 좋아요 수 -1
     * 있는데 좋아요 상태라면 Unlike 상태로 바꾸고, 좋아요 수 -2
     */
    public void switchUnlike(Long questionId, Member member) {
        Question findQuestion = findVerifyQuestion(questionId);
        int likeCnt = findQuestion.getLikeCnt();

        Optional<QuestionLike> optionalQuestionLike = questionLikeRepository.findByMemberIdAndQuestionId(member.getId(), questionId);

        if (optionalQuestionLike.isPresent()) {
            QuestionLike findQuestionLike = optionalQuestionLike.get();

            if (findQuestionLike.getLikeType().equals(LikeType.LIKE)) {
                findQuestionLike.setLikeType(LikeType.UNLIKE);
                findQuestion.setLikeCnt(likeCnt - 2);
            }
        } else {
            QuestionLike questionLike = new QuestionLike();
            questionLike.setQuestion(findQuestion);
            questionLike.setMember(member);
            questionLike.setLikeType(LikeType.UNLIKE);

            questionLikeRepository.save(questionLike);

            findQuestion.setLikeCnt(likeCnt - 1);
        }
    }

    /**
     * 질문 북마크
     */
    public void switchBookmark(Long questionId, Member member) {
        Question findQuestion = findVerifyQuestion(questionId);

        // memberId 와 questionId 로 등록된 bookmark 있는 지 확인
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByMemberIdAndQuestionId(member.getId(), questionId);

        if (optionalBookmark.isPresent()) {
            bookmarkRepository.delete(optionalBookmark.get());
        } else {
            Bookmark bookmark = new Bookmark();
            bookmark.setMember(member);
            bookmark.setQuestion(findQuestion);

            bookmarkRepository.save(bookmark);
        }
    }

    /**
     * 질문 검색 (태그 부분 빼면 완성)
     * Default : 제목 + 내용
     * User : 회원 번호
     * Tag : 태그 - 검색한 태그가 없을 때 예외 처리 필요 @@@
     */
    public Page<Question> searchQuestions(int page, String keyword, String type) {
        // 타입 대문자로 변경
        type = type.toUpperCase();

        if (type.equals("USER")) {
            Long memberId = Long.valueOf(keyword);
            return questionRepository.findByMemberId(memberId, PageRequest.of(page, 15));
        } else if (type.equals("TAG")) {
            keyword = keyword.toUpperCase();

            Tag tag = tagRepository.findByName(keyword).get();  // 검색한 태그가 없을 때 예외처리 필요

            List<Long> questionIds = tag.getQuestionTags().stream()
                    .map(questionTag -> questionTag.getQuestion().getId())
                    .collect(Collectors.toList());

            return questionRepository.findByIdIn(questionIds, PageRequest.of(page, 15));

        } else {
            // 키워드가 포함된 글들 가져오기 위해서 키워드의 양 옆에 % 추가
            keyword = "%" + keyword + "%";

            // Default 검색
            return questionRepository.findByTitleLikeOrContentLike(keyword, keyword, PageRequest.of(page, 15));
        }
    }


    // 검증된 question 레포에서 찾기
    public Question findVerifyQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion
                .orElseThrow(() -> new RuntimeException("잘못된 질문 아이디")); // 비즈니스 예외처리 넣기

        return findQuestion;
    }

    // bookmark 여부 찾기
    public boolean getBookmarked(Long memberId, Long questionId) {
        return bookmarkRepository.findByMemberIdAndQuestionId(memberId, questionId)
                .isPresent();
    }

    // like status 찾기
    public LikeType getLikeStatus(Long memberId, Long questionId) {
        Optional<QuestionLike> optionalQuestionLike = questionLikeRepository.findByMemberIdAndQuestionId(memberId, questionId);

        if (optionalQuestionLike.isPresent()) {
            return optionalQuestionLike.get().getLikeType();
        } else {
            return null;
        }
    }

}
