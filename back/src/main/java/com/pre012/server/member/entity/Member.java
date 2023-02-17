package com.pre012.server.member.entity;

import com.pre012.server.question.entity.Question;
import com.pre012.server.question.entity.QuestionComment;
import com.pre012.server.question.entity.QuestionLike;
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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<QuestionComment> questionComments;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<QuestionLike> memberQuestionLikes;

}
