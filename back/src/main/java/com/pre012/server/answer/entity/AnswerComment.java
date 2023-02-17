package com.pre012.server.answer.entity;

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
    @GeneratedValue
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
