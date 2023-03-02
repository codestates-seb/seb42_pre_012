package com.pre012.server.answer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pre012.server.common.audit.Auditable;
import com.pre012.server.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class AnswerComment extends Auditable {
    @Id
    @Column(name = "answer_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public AnswerComment(Long id, String content, Answer answer, Member member) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.member = member;
    }
}
