package com.pre012.server.question.service;

import com.pre012.server.question.entity.Question;
import com.pre012.server.question.entity.QuestionTag;
import com.pre012.server.question.repository.QuestionTagRepository;
import com.pre012.server.tag.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionTagService {
    private final QuestionTagRepository questionTagRepository;

    public QuestionTagService(QuestionTagRepository questionTagRepository) {
        this.questionTagRepository = questionTagRepository;
    }

    private void createQuestionTag(Question question, Tag tag) {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestion(question);
        questionTag.setTag(tag);

        questionTagRepository.save(questionTag);
    }

    public void createQuestionTags(Question question, List<Tag> tags) {
        for (Tag tag : tags) {
            createQuestionTag(question, tag);
        }
    }


    /**
     * 질문 수정했을 때
     * 그냥 그 questionId의 QuestionTag 날려버리고 생성해서 넣어버림 ㅠㅜ
     */
    public void updateQuestionTags(Question question, List<Tag> tags) {
        questionTagRepository.deleteByQuestionId(question.getId());

        tags.stream()
                .forEach(tag ->
                        createQuestionTag(question, tag));

    }

}
