package com.pre012.server.question.entity;

import com.pre012.server.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "MEMBER_QUESTION_LIKE")
public class QuestionLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionLikeId;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private LikeType likeType = LikeType.NOT_SELECTED;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    public enum LikeType {
        NOT_SELECTED("선택 안함"),
        QUESTION_LIKE("질문 좋아요"),
        QUESTION_UNLIKE("질문 싫어요");

        @Getter
        private String type;

        LikeType(String type) {
            this.type = type;
        }
    }
}
