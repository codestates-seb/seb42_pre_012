package com.pre012.server.answer.service;

import com.pre012.server.answer.dto.AnswerCommentPostDto;
import com.pre012.server.answer.dto.AnswerCommentResponseDto;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.answer.repository.AnswerCommentRepository;
import com.pre012.server.answer.repository.AnswerLikeRepository;
import com.pre012.server.answer.repository.AnswerRepository;
import com.pre012.server.exception.BusinessLogicException;
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

    public Answer createAnswer(Answer answer, Long question_id){
        /* 1. 멤버 id가 존재하는지 확인 / answerPostDto내에 있는 member_id를 통해
           2. answer 엔티티에 question_id를 통해 찾은 question 부여 / set
           3. answer 엔티티에 멤버 부여
         */
        Question question = new Question();
        Member member = new Member("a@ma.com","asdfasdf","park", MemberStatus.MEMBER_ACTIVE,"asdfsf");
        //memberService.verifyMember(answer.getMember().getId());
        answer.setQuestion(question);
        answer.setMember(member);
        //좋아요 수 초기값 설정
        answer.setLikeCnt(0);

        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer,Long member_id) {
        Answer findAnswer = findVerifiedAnswer(answer.getId());
        verifiedAuthorization(findAnswer,member_id);

        Optional.ofNullable(answer.getContent())
                .ifPresent(content->findAnswer.setContent(content));

        return answerRepository.save(findAnswer);
    }

    public void deleteAnswer(Long answer_id,Long member_id) {
        Answer answer = findVerifiedAnswer(answer_id);
        verifiedAuthorization(answer,member_id);

        answerRepository.delete(answer);
    }
    //넘겨줄 데이터 임의로 설정한 상태
    public Page<Answer> findAnswers (Long question_id,int page,String sortedBy) {
        if (sortedBy.equals("createdAt")) {
            return answerRepository.findByQuestion_Id(question_id,PageRequest.of(page, size,
                    Sort.by("createdAt").descending()));
        } else if (sortedBy.equals("modifiedAt")) {
            return answerRepository.findByQuestion_Id(question_id,PageRequest.of(page, size,
                    Sort.by("modifiedAt").descending()));
        } else {
            return answerRepository.findByQuestion_Id(question_id,PageRequest.of(page, size,
                    Sort.by("likeCnt").descending()));
        }
    }
    public Answer findVerifiedAnswer(Long answer_id){
        //cascade가 어떻게 적용될지 모르니 각각 해봐야할듯 검사를? (question과 같이)
        Optional<Answer> optionalAnswer =
                answerRepository.findById(answer_id);
        Answer findAnswer=
                optionalAnswer.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        return findAnswer;
    }
    //임시용 권한 확인 메서드
    public void verifiedAuthorization(Answer answer, Long member_id) {
        if (!Objects.equals(member_id, answer.getMember().getId()))
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
    }

    public void verifiedAuthorization(AnswerComment answerComment, Long member_id) {
        if (!Objects.equals(member_id, answerComment.getMember().getId()))
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
    }


    public AnswerComment createAnswerComment(AnswerComment answerComment,Long answer_id){
        //존재하는 유저인지 검증 구현
        Answer findAnswer = findVerifiedAnswer(answer_id);
        answerComment.setAnswer(findAnswer);
        Member member = new Member("b@ma.com","asdfasdf","kim", MemberStatus.MEMBER_ACTIVE,"asdfsf");
        answerComment.setMember(member);
        return answerCommentRepository.save(answerComment);
    }

    public AnswerComment updateAnswerComment(AnswerComment answerComment, Long member_id){
        AnswerComment findAnswerComment = findVerifiedAnswerComment(answerComment.getId());
            verifiedAuthorization(findAnswerComment,member_id);

            Optional.ofNullable(answerComment.getContent())
                    .ifPresent(content->findAnswerComment.setContent(content));

            return answerCommentRepository.save(findAnswerComment);
        }
    public void deleteAnswerComment(Long comment_id, Long member_id){
        AnswerComment answerComment = findVerifiedAnswerComment(comment_id);
        verifiedAuthorization(answerComment,member_id);

        answerCommentRepository.delete(answerComment);
    }


    public AnswerComment findVerifiedAnswerComment(Long comment_id){
        //cascade가 어떻게 적용될지 모르니 각각 해봐야할듯 검사를? (question과 같이)
        Optional<AnswerComment> optionalAnswerComment =
                answerCommentRepository.findById(comment_id);
        AnswerComment findAnswerComment=
                optionalAnswerComment.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findAnswerComment;
    }

    @Transactional
    public void like(Long answer_id,Long member_id){
        Answer answer = findVerifiedAnswer(answer_id);
        Member member = memberService.findVerifiedMember(member_id);
        AnswerLike answerLike = new AnswerLike();
        answerLike.setMember(member);
        answerLike.setAnswer(answer);
        Optional<AnswerLike> optionalAnswerLike = answerLikeRepository.findByMemberAndAnswer(member_id,answer_id);
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
            if(likeType.equals(LikeType.LIKE)) {

            }
            else if(likeType.equals(LikeType.UNLIKE)){
                likeCnt+=2;
                answer.setLikeCnt(likeCnt);
                findAnswerLike.setLikeType(LikeType.LIKE);
                answerLikeRepository.save(answerLike);
                answerRepository.save(answer);
            }
        }
        //List<AnswerLike> answerLikes = answer.getMemberLikes();
        //member_id로 member조회 후, answerLike repo에서 조회
        /*
        1.answerLike에서 find를 함 멤버를 --> 만약에 없으면 로직 실행
        2.해당 멤버의 likeType을 바꿈 --> 이걸 어케할까?
         */
    }

    public void unlike(Long answer_id,Long member_id){
        Answer answer = findVerifiedAnswer(answer_id);
        Member member = memberService.findVerifiedMember(member_id);
        AnswerLike answerLike = new AnswerLike();
        answerLike.setMember(member);
        answerLike.setAnswer(answer);
        Optional<AnswerLike> optionalAnswerLike = answerLikeRepository.findByMemberAndAnswer(member_id,answer_id);
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
            if(likeType.equals(LikeType.UNLIKE)) {

            }
            else if(likeType.equals(LikeType.LIKE)){
                likeCnt-=2;
                answer.setLikeCnt(likeCnt);
                findAnswerLike.setLikeType(LikeType.LIKE);
                answerLikeRepository.save(answerLike);
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

//    public boolean didLiked(Answer answer,Long member_id){
//        //member_id로 찾은 member 객체를 넣어야하는지?
//        if(answer.getMemberLikes().contains(member_id)){
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
}
