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
import org.hibernate.annotations.Formula;

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

    // 이미지 넣는 부분
    @Column(length = 200)
    private String image_path;

    @Column
    private String content;

    @Column
    private int viewCnt = 0;

    @Column
    private int likeCnt = 0;

    @Formula("(SELECT COUNT(1) FROM ANSWER a WHERE a.question_id = question_id)")
    private int answerCnt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 1 : N
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionComment> comments = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionLike> memberLikes = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionTag> tags = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();


    /*
     *  양방향 매핑 설정
     */

    public void setAnswer(Answer answer) {
        this.answers.add(answer);
        if (answer.getQuestion() != this) {
            answer.setQuestion(this);
        }
    }

    public void setComments(QuestionComment questionComment) {
        this.comments.add(questionComment);
        if (questionComment.getQuestion() != this) {
            questionComment.setQuestion(this);
        }
    }

    public void setMemberLikes(QuestionLike questionLike) {
        this.memberLikes.add(questionLike);
        if (questionLike.getQuestion() != this) {
            questionLike.setQuestion(this);
        }
    }

    public void setTags(QuestionTag tag) {
        this.tags.add(tag);
        if (tag.getQuestion() != this) {
            tag.setQuestion(this);
        }
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmarks.add(bookmark);
        if (bookmark.getQuestion() != this) {
            bookmark.setQuestion(this);
        }
    }
}
