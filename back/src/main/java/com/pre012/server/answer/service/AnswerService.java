package com.pre012.server.answer.service;

import com.pre012.server.answer.dto.AnswerCommentPostDto;
import com.pre012.server.answer.dto.AnswerCommentResponseDto;
import com.pre012.server.answer.entity.Answer;
import com.pre012.server.answer.entity.AnswerComment;
import com.pre012.server.answer.repository.AnswerCommentRepository;
import com.pre012.server.answer.repository.AnswerLikeRepository;
import com.pre012.server.answer.repository.AnswerRepository;
import com.pre012.server.member.entity.AnswerLike;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.enums.LikeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Transactional 애너테이션 정확한 기능 알아볼 것
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final AnswerCommentRepository answerCommentRepository;
    private final int size = 30;

    public AnswerService(AnswerRepository answerRepository, AnswerLikeRepository answerLikeRepository, AnswerCommentRepository answerCommentRepository) {
        this.answerRepository = answerRepository;
        this.answerLikeRepository = answerLikeRepository;
        this.answerCommentRepository = answerCommentRepository;
    }

    public Answer createAnswer(Answer answer, Long question_id){
        /* 1. 멤버 id가 존재하는지 확인 / answerPostDto내에 있는 member_id를 통해
           2. answer 엔티티에 question_id를 통해 찾은 question 부여 / set
           3. answer 엔티티에 멤버 부여
         */
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

    public void deleteAnswer(Long answer_id,Long member_id) throws Exception {
        Answer answer = findVerifiedAnswer(answer_id);
        verifiedAuthorization(answer,member_id);

        answerRepository.delete(answer);
    }
    //넘겨줄 데이터 임의로 설정한 상태
    public Page<Answer> findAnswers (int page,String sortedBy) {
        if (sortedBy.equals("createdAt")) {
            return answerRepository.findAll(PageRequest.of(page, size,
                    Sort.by("createdAt").descending()));
        } else if (sortedBy.equals("modifiedAt")) {
            return answerRepository.findAll(PageRequest.of(page, size,
                    Sort.by("modifiedAt").descending()));
        } else {
            return answerRepository.findAll(PageRequest.of(page, size,
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
    public void verifiedAuthorization(Answer answer, Long member_id) throws Exception {
       if(member_id!=answer.getMember().getId()){
           throw new Exception("오류");
       }
    }

    public void verifiedAuthorization(AnswerComment answerComment, Long member_id) throws Exception {
        if(member_id!=answerComment.getMember().getId()){
            throw new Exception("오류");
        }
    }


    public AnswerComment createAnswerComment(AnswerComment answerComment,Long answer_id){
        //존재하는 유저인지 검증 구현
        Answer findAnswer = findVerifiedAnswer(answer_id);
        answerComment.setAnswer(findAnswer);
        answerComment.setMember(new Member());
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


    public void like(Long answer_id,Long member_id){
        Answer answer = findVerifiedAnswer(answer_id);
        AnswerLike answerLike = new AnswerLike();
        AnswerLike findAnswerLike = answerLikeRepository.findByMemberAndAnswer(member,answer);
        int likeCnt = answer.getLikeCnt();
        //멤버도 찾음
        LikeType likeType = getLikeType(answer,member);
        if(likeType.equals(LikeType.LIKE)) {

        }
        else if(likeType.equals(LikeType.UNLIKE)){
            likeCnt+=2;
            answer.setLikeCnt(likeCnt);
            findAnswerLike.setLikeType(LikeType.LIKE);
        }
        else{
            likeCnt++;
            answer.setLikeCnt(likeCnt);
            answerLike.setMember(member);
            answerLike.setAnswer(answer);
            answerLike.setLikeType(LikeType.LIKE);
            //answer.setMemberLikes(answerLike);
            answerLikeRepository.save(answerLike);
            answerRepository.save(answer);
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
        AnswerLike answerLike = new AnswerLike();
        AnswerLike findAnswerLike = answerLikeRepository.findByMemberAndAnswer(member,answer); //->null값이 넘어오는지?
        int likeCnt = answer.getLikeCnt();
        //멤버도 찾음
        LikeType likeType = getLikeType(answer,member);
        if(likeType.equals(LikeType.UNLIKE)) {

        }
        else if(likeType.equals(LikeType.LIKE)){
            likeCnt-=2;
            answer.setLikeCnt(likeCnt);
            findAnswerLike.setLikeType(LikeType.LIKE);
        }
        else{
            likeCnt--;
            answer.setLikeCnt(likeCnt);
            answerLike.setMember(member);
            answerLike.setAnswer(answer);
            answerLike.setLikeType(LikeType.LIKE);
            //answer.setMemberLikes(answerLike);
            answerLikeRepository.save(answerLike);
            answerRepository.save(answer);
        }
    }
    public LikeType getLikeType(Member member,Answer answer){
        Optional<AnswerLike> optionalAnswerLike
                = answerLikeRepository.findByMemberAndAnswer(member,answer);
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
