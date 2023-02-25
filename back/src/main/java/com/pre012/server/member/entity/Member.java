package com.pre012.server.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.common.audit.Auditable;
import com.pre012.server.member.enums.MemberStatus;
import com.pre012.server.question.entity.Question;

import lombok.*;
import org.hibernate.annotations.Formula;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @Column(length = 200)
    private String profileImagePath;

    // DB에 member_role 저장, @OneToMany로 관리
    @ElementCollection(fetch = FetchType.EAGER)
    // JoinColumn 이름은 "member_id"로 설정
    @CollectionTable(name = "member_role", joinColumns = @JoinColumn(name = "member_id"))
    private List<String> roles = new ArrayList<>();

    @Formula("(SELECT count(1) FROM question q WHERE q.member_id = member_id)")
    private int questionCnt;

    @Formula("(SELECT count(1) FROM answer a WHERE a.member_id = member_id)")
    private int answerCnt;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();
    
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();
    
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarks = new ArrayList<>();
    
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<QuestionLike> questionLikes = new ArrayList<>();
    
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<AnswerLike> answerLikes = new ArrayList<>();

    /*
     *  양방향 매핑 설정
     */
    public void setQuestion(Question question) {
        this.questions.add(question);
        if (question.getMember() != this) {
            question.setMember(this);
        }
    }

    public void setAnswer(Answer answer) {
        this.answers.add(answer);
        if (answer.getMember() != this) {
            answer.setMember(this);
        }
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmarks.add(bookmark);
        if (bookmark.getMember() != this) {
            bookmark.setMember(this);
        }
    }

    public void setQuestionLikes(QuestionLike questionLike) {
        this.questionLikes.add(questionLike);
        if (questionLike.getMember() != this) {
            questionLike.setMember(this);
        }
    }

    public void setAnswerLikes(AnswerLike answerLike) {
        this.answerLikes.add(answerLike);
        if (answerLike.getMember() != this) {
            answerLike.setMember(this);
        }
    }
}
