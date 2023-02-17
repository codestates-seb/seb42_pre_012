package com.pre012.server.question.entity;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.common.audit.Auditable;
import com.pre012.server.member.entity.Bookmark;
import com.pre012.server.member.entity.Member;
import com.pre012.server.member.entity.QuestionLike;

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
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // 이미지를 중간에 넣는다면 배열로 해야하는 걸까나? 이미지는 byte 로 오지 않나..?
    @Column(length = 200)
    private String image_path;  
    
    @Column
    private String content;

    @Column
    private int hits;

    @Column
    private int likes;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL) // ↓ cascade들 우선 둘게요!!
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionLike> memberLikes = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionTag> tags = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();
}
