package com.pre012.server.question.service;

import com.pre012.server.answer.repository.AnswerRepository;
import com.pre012.server.exception.BusinessLogicException;
import com.pre012.server.exception.ExceptionCode;
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
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, QuestionLikeRepository questionLikeRepository, BookmarkRepository bookmarkRepository, QuestionTagRepository questionTagRepository, TagRepository tagRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.questionLikeRepository = questionLikeRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.questionTagRepository = questionTagRepository;
        this.tagRepository = tagRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * ?????? ?????? (??????)
     */
    public Question createQuestion(Question question) {

        Question savedQuestion = questionRepository.save(question);

        return savedQuestion;
    }

    /**
     * ?????? ??????
     */
    public Question updateQuestion(Question question) {
        // DB ?????? Question ????????????
        Question findQuestion = findVerifyQuestion(question.getId());

        if (findQuestion.getMember().getId() != question.getMember().getId()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }

        findQuestion.setTitle(question.getTitle());
        findQuestion.setContent(question.getContent());

        return findQuestion;

    }

    /**
     * ?????? ??????
     */
    public void deleteQuestion(Long questionId, Long memberId) {
        Question findQuestion = findVerifyQuestion(questionId);

        // question ??? ????????? memberId ??? ????????????????????? ?????? memberId ??????
        if (!findQuestion.getMember().getId().equals(memberId)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }

        // answer ????????? ?????????
        answerRepository.deleteById(findQuestion.getId());

        questionRepository.deleteById(questionId);
    }

    /**
     * ?????? ?????? ??????
     * Unanswered : answerCnt == 0
     * Interesting : viewCnt ?????? ???
     * Hot : answerCnt ?????? ???
     */
    public Page<Question> findQuestions(int page, String sortedBy) {
        // ?????? ?????? ???????????? ??????
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

            // Default : NEWEST (?????? ?????????)
            return questionRepository.findAll(PageRequest.of(page, 15,
                    Sort.by("createdAt").descending()));
        }
    }

    /**
     * ?????? ?????? ??????
     * viewCnt + 1
     */
    public Question findQuestion(Long questionId) {
        Question findQuestion = findVerifyQuestion(questionId);
        int viewCnt = findQuestion.getViewCnt();
        findQuestion.setViewCnt(viewCnt + 1);

        return findQuestion;
    }

    /**
     * ?????? ????????? (?????? ???????????? ??????)
     * QuestionLike ??? ????????? ????????? ???????????? ???????????? ????????????, ????????? ??? (likeCnt) + 1
     * ????????? ????????? ???????????? Like ????????? ?????????, ????????? ??? + 2
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

            questionLikeRepository.save(questionLike); // ?????? ??????????????? repo ??? save ??????

            findQuestion.setLikeCnt(likeCnt + 1);
        }
    }

    /**
     * ?????? ????????? (?????? ???????????? ??????)
     * QuestionLike ??? ????????? ????????? QuestionLike ???????????? ????????? ????????? ??????. ????????? ??? -1
     * ????????? ????????? ???????????? Unlike ????????? ?????????, ????????? ??? -2
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
     * ?????? ?????????
     */
    public void switchBookmark(Long questionId, Member member) {
        Question findQuestion = findVerifyQuestion(questionId);

        // memberId ??? questionId ??? ????????? bookmark ?????? ??? ??????
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
     * ?????? ?????? (?????? ?????? ?????? ??????)
     * Default : ?????? + ??????
     * User : ?????? ??????
     * Tag : ?????? - ????????? ????????? ?????? ??? ?????? ?????? ?????? @@@
     */
    public Page<Question> searchQuestions(int page, String keyword, String type) {
        // ?????? ???????????? ??????
        type = type.toUpperCase();

        if (type.equals("USER")) {
            Long memberId = Long.valueOf(keyword);
            return questionRepository.findByMemberId(memberId, PageRequest.of(page, 15));
        } else if (type.equals("TAG")) {
            keyword = keyword.toUpperCase();

            Tag tag = tagRepository.findByName(keyword)
                    .orElseGet(Tag::new); // DB??? ???????????? ??? ????????? ?????? ??????????????? ????????? ???????????? ????????? ???.
//                    .orElseThrow(()-> new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND));// ????????? ????????? ?????? ??? ???????????? ??????

            List<Long> questionIds = tag.getQuestionTags().stream()
                    .map(questionTag -> questionTag.getQuestion().getId())
                    .collect(Collectors.toList());

            return questionRepository.findByIdIn(questionIds, PageRequest.of(page, 15));

        } else {
            // ???????????? ????????? ?????? ???????????? ????????? ???????????? ??? ?????? % ??????
            keyword = "%" + keyword + "%";

            // Default ??????
            return questionRepository.findByTitleLikeOrContentLike(keyword, keyword, PageRequest.of(page, 15));
        }
    }


    // ????????? question ???????????? ??????
    public Question findVerifyQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }

    // bookmark ?????? ??????
    public boolean getBookmarked(Long memberId, Long questionId) {
        return bookmarkRepository.findByMemberIdAndQuestionId(memberId, questionId)
                .isPresent();
    }

    // like status ??????
    public LikeType getLikeStatus(Long memberId, Long questionId) {
        Optional<QuestionLike> optionalQuestionLike = questionLikeRepository.findByMemberIdAndQuestionId(memberId, questionId);

        if (optionalQuestionLike.isPresent()) {
            return optionalQuestionLike.get().getLikeType();
        } else {
            return null;
        }
    }

}
