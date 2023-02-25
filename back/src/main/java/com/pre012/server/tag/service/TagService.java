package com.pre012.server.tag.service;

import com.pre012.server.tag.entity.Tag;
import com.pre012.server.tag.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * 질문 등록, 질문 수정 시 태그 저장
     */

    // tag 이름으로 repo에서 찾고 없으면 새로 태그 저장한 값 리턴.
    public List<Tag> findVerifyTags(List<Tag> tags) {
        return tags.stream()
                .map(tag -> {
                   Tag resultTag = tagRepository.findByName(tag.getName())
                                        .orElse(createTag(tag));

                   return resultTag;
                }).collect(Collectors.toList());
    }

    // 이름만 있는 태그 repo에 저장
    private Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }




}
