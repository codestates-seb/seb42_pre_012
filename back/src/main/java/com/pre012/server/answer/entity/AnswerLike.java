package com.pre012.server.answer.entity;

import com.pre012.server.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class AnswerLike {
    @Id
    @Column(name="answer_like_id")
    @GeneratedValue
    private Long id;

    @Enumerated(value=EnumType.STRING)
    @Column(nullable = true)
    private LikeType likeType;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="answer_id")
    private Answer answer;

    enum LikeType{
        Like,
        unLike;
    }

}
