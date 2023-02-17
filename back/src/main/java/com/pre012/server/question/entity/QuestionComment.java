package com.pre012.server.question.entity;

import com.pre012.server.common.audit.Auditable;
import com.pre012.server.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class QuestionComment extends Auditable {
    @Id
    @Column(name = "question_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content; // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
