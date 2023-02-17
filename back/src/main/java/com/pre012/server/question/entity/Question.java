package com.pre012.server.question.entity;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.common.audit.Auditable;
import com.pre012.server.member.entity.Bookmark;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.entity.QuestionLike;
import com.pre012.server.tag.entity.QuestionTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Column(nullable = false)
    private String title;

    // 이미지를 중간에 넣는다면 배열로 해야하는 걸까나? 이미지는 byte 로 오지 않나..?
    @Column(nullable = false)
    private String content;

    @Column(name = "HITS")
    private int hit;

    @Column(name = "LIKES")
    private int like;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionComment> questionComments = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionLike> memberQuestionLikes = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionTag> questionTags = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();
}
