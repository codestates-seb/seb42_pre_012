package com.pre012.server.tag.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.pre012.server.question.entity.QuestionTag;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Tag {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String content; // 태그 상세 설명

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "tag",  fetch = FetchType.LAZY)
    private List<QuestionTag> questionTags = new ArrayList<>();

    // 양방향 매핑 설정

    public void setQuestionTags(QuestionTag tag) {
        this.questionTags.add(tag);
        if (tag.getTag() != this) {
            tag.setTag(this);
        }
    }
}
