package com.pre012.server.answer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pre012.server.common.audit.Auditable;
import com.pre012.server.member.entity.AnswerLike;
import com.pre012.server.member.entity.Member;
import com.pre012.server.question.entity.Question;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Answer extends Auditable {
    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //글 내용
    @Column
    private String content;

    //좋아요 수
    @Column(nullable = false)
    private int likeCnt;

    //이미지 가져오는 법 확인
    @Column(length = 200)
    private String imagePath;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "answer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AnswerComment> comments = new ArrayList<>();


    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "answer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AnswerLike> AnswerLikes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    //한번에 양방향 관계 설정을 위한 메소드
    public void setComments(AnswerComment comment) {
        this.comments.add(comment);
        if (comment.getAnswer() != this) {
            comment.setAnswer(this);
        }
    }

    public void setAnswerLikes(AnswerLike answerLike) {
        this.AnswerLikes.add(answerLike);
        if (answerLike.getAnswer() != this) {
            answerLike.setAnswer(this);
        }
    }

}
