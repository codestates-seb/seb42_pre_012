package com.pre012.server.answer.entity;

import com.pre012.server.common.audit.Auditable;
import com.pre012.server.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Answer extends Auditable {
    @Id
    @Column(name="answer_id")
    @GeneratedValue
    private Long id;

    //글 내용
    @Column
    private String content;

    //좋아요 수
    @Column(nullable = false)
    private int likes;

    //조회 수
    @Column(nullable = false)
    private int hits;


    @OneToMany(mappedBy = "answer")
    private List<AnswerComment> comments = new ArrayList<>();

    //이미지 가져오는 법 확인

    @ManyToOne
    @JoinColumn("member_id")
    private Member member;

    @ManyToOne
    @JoinColumn("question_id")
    private Question question;

    //한번에 양방향 관계 설정을 위한 메소드
    public void setAnswerComment(AnswerComment comment){
        this.comments.add(comment);
        if(comment.getAnswer()!=this){
            comment.setAnswer(this);
        }
    }
}
