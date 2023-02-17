package com.pre012.server.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.pre012.server.answer.entity.Answer;
import com.pre012.server.common.audit.Auditable;
import com.pre012.server.question.entity.Question;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String displayName;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();
    
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();
    
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
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
