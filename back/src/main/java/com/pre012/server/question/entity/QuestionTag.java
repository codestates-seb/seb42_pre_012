package com.pre012.server.question.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.pre012.server.tag.entity.Tag;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class QuestionTag {
    @Id
    @Column(name = "question_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
}
