package com.pre012.server.answer.service;

import com.pre012.server.answer.dto.AnswerCommentPostDto;
import com.pre012.server.answer.dto.AnswerCommentResponseDto;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.answer.repository.AnswerCommentRepository;
import com.pre012.server.answer.repository.AnswerLikeRepository;
import com.pre012.server.answer.repository.AnswerRepository;
import com.pre012.server.exception.BusinessLogicException;
import com.pre012.server.exception.ErrorResponseDto;
import com.pre012.server.exception.ExceptionCode;
import com.pre012.server.member.entity.AnswerLike;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.enums.LikeType;
import com.pre012.server.member.enums.MemberStatus;
import com.pre012.server.member.service.MemberService;
import com.pre012.server.question.entity.Question;
import com.pre012.server.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Transactional 애너테이션 정확한 기능 알아볼 것
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final AnswerCommentRepository answerCommentRepository;
    private final MemberService memberService;
    private final QuestionService questionService;
    private final int size = 30;

    public AnswerService(AnswerRepository answerRepository, AnswerLikeRepository answerLikeRepository, AnswerCommentRepository answerCommentRepository, MemberService memberService,QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.answerLikeRepository = answerLikeRepository;
        this.answerCommentRepository = answerCommentRepository;
        this.memberService = memberService;
        this.questionService = questionService;
    }

    public Answer createAnswer(Answer answer, Long questionId){
        /* 1. 멤버 id가 존재하는지 확인 / answerPostDto내에 있는 member_id를 통해
           2. answer 엔티티에 question_id를 통해 찾은 question 부여 / set
           3. answer 엔티티에 멤버 부여
         */
        Question question = questionService.findVerifyQuestion(questionId);
        Member member = memberService.findVerifiedMember(answer.getMember().getId());
        answer.setQuestion(question);
        answer.setMember(member);
        //좋아요 수 초기값 설정
        answer.setLikeCnt(0);
        question.setAnswer(answer);
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer,Long memberId) {
        Answer findAnswer = findVerifiedAnswer(answer.getId());
        verifiedAuthorization(findAnswer,memberId);

        Optional.ofNullable(answer.getContent())
                .ifPresent(content->findAnswer.setContent(content));

        return answerRepository.save(findAnswer);
    }

    public void deleteAnswer(Long answerId,Long memberId) {
        Answer answer = findVerifiedAnswer(answerId);
        verifiedAuthorization(answer,memberId);

        answerRepository.delete(answer);
    }

    public Page<Answer> findAnswers (Long questionId,int page,String sortedBy) {
        if (sortedBy.equals("createdAt")) {
            return answerRepository.findByQuestion_Id(questionId,PageRequest.of(page, size,
                    Sort.by("createdAt").descending()));
        } else if (sortedBy.equals("modifiedAt")) {
            return answerRepository.findByQuestion_Id(questionId,PageRequest.of(page, size,
                    Sort.by("modifiedAt").descending()));
        } else {
            return answerRepository.findByQuestion_Id(questionId,PageRequest.of(page, size,
                    Sort.by("likeCnt").descending()));
        }
    }
    public Answer findVerifiedAnswer(Long answerId){

        Optional<Answer> optionalAnswer =
                answerRepository.findById(answerId);
        Answer findAnswer=
                optionalAnswer.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        return findAnswer;
    }

    public void verifiedAuthorization(Answer answer, Long memberId) {
        if (!Objects.equals(memberId, answer.getMember().getId()))
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
    }

    public void verifiedAuthorization(AnswerComment answerComment, Long memberId) {
        if (!Objects.equals(memberId, answerComment.getMember().getId()))
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
    }


    public AnswerComment createAnswerComment(AnswerComment answerComment,Long answerId){
        Answer findAnswer = findVerifiedAnswer(answerId);
        answerComment.setAnswer(findAnswer);
        Member member = memberService.findVerifiedMember(answerComment.getMember().getId());
        answerComment.setMember(member);
        return answerCommentRepository.save(answerComment);
    }

    public AnswerComment updateAnswerComment(AnswerComment answerComment, Long memberId){
        AnswerComment findAnswerComment = findVerifiedAnswerComment(answerComment.getId());
        verifiedAuthorization(findAnswerComment,memberId);

        Optional.ofNullable(answerComment.getContent())
                .ifPresent(content->findAnswerComment.setContent(content));

        return answerCommentRepository.save(findAnswerComment);
        }
    public void deleteAnswerComment(Long commentId, Long memberId){
        AnswerComment answerComment = findVerifiedAnswerComment(commentId);
        verifiedAuthorization(answerComment,memberId);

        answerCommentRepository.delete(answerComment);
    }


    public AnswerComment findVerifiedAnswerComment(Long commentId){
        //cascade가 어떻게 적용될지 모르니 각각 해봐야할듯 검사를? (question과 같이)
        Optional<AnswerComment> optionalAnswerComment =
                answerCommentRepository.findById(commentId);
        AnswerComment findAnswerComment=
                optionalAnswerComment.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findAnswerComment;
    }

    /**
    한번 더 save를 안하면 db에 반영이 안되서 일단은 넣어놨는데, tranjaction 처리로 해결하면 된다고 하셨는데, 정확히 이해가 안가 일단 그대로 두었습니다.
     */
    public void like(Long answerId,Long memberId){
        Answer answer = findVerifiedAnswer(answerId);
        Member member = memberService.findVerifiedMember(memberId);
        AnswerLike answerLike = new AnswerLike();
        answerLike.setMember(member);
        answerLike.setAnswer(answer);
        Optional<AnswerLike> optionalAnswerLike = answerLikeRepository.findByMemberAndAnswer(memberId,answerId);
        int likeCnt = answer.getLikeCnt();
        if(optionalAnswerLike.isEmpty()){
            answerLike.setLikeType(LikeType.LIKE);
            likeCnt++;
            answer.setLikeCnt(likeCnt);
            answerLikeRepository.save(answerLike);
            answerRepository.save(answer);
        }
        else
        {
            AnswerLike findAnswerLike = optionalAnswerLike.get();
            LikeType likeType = findAnswerLike.getLikeType();

            if(likeType.equals(LikeType.UNLIKE)){
                likeCnt+=2;
                answer.setLikeCnt(likeCnt);
                findAnswerLike.setLikeType(LikeType.LIKE);
                answerRepository.save(answer);
            }
        }
    }

    public void unlike(Long answerId,Long memberId){
        Answer answer = findVerifiedAnswer(answerId);
        Member member = memberService.findVerifiedMember(memberId);
        AnswerLike answerLike = new AnswerLike();
        answerLike.setMember(member);
        answerLike.setAnswer(answer);
        Optional<AnswerLike> optionalAnswerLike = answerLikeRepository.findByMemberAndAnswer(memberId,answerId);
        int likeCnt = answer.getLikeCnt();

        if(optionalAnswerLike.isEmpty()){
            answerLike.setLikeType(LikeType.UNLIKE);
            likeCnt--;
            answer.setLikeCnt(likeCnt);
            answerLikeRepository.save(answerLike);
            answerRepository.save(answer);
        }
        else
        {
            AnswerLike findAnswerLike = optionalAnswerLike.get();
            LikeType likeType = findAnswerLike.getLikeType();
            if(likeType.equals(LikeType.LIKE)){
                likeCnt-=2;
                answer.setLikeCnt(likeCnt);
                findAnswerLike.setLikeType(LikeType.UNLIKE);
                answerRepository.save(answer);
            }
        }
    }
    public LikeType getLikeType(Member member,Answer answer){
        Optional<AnswerLike> optionalAnswerLike
                = answerLikeRepository.findByMemberAndAnswer(member.getId(),answer.getId());
        AnswerLike findAnswerLike =
                optionalAnswerLike.orElse(null);
        return findAnswerLike.getLikeType();
    }
}
