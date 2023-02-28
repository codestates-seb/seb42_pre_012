package com.pre012.server.tag.service;

import com.pre012.server.exception.BusinessLogicException;
import com.pre012.server.exception.ExceptionCode;
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

    // Tag 새로 만드는 메서드
    private Tag createTag(Tag tag) {
        Tag createdTag = new Tag();
        createdTag.setName(tag.getName().toLowerCase());

        return tagRepository.save(createdTag);
    }

    // Repository 에 있는 Tag 인지 확인하는 메서드
    private Tag verifyExistsTag(Tag tag) {
        Optional<Tag> optionalTag = tagRepository.findByName(tag.getName());

        if (optionalTag.isPresent()) {
            return optionalTag.get();
        } else {
            return createTag(tag);     // repo 에 없으면 태그 새로 만듦.
        }
    }

    // 검증된 Tag 리스트를 반환하는 메서드
    public List<Tag> findVerifyTags(List<Tag> tags) {
        if (tags.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND);
        }

        return tags.stream()
                .map(tag -> verifyExistsTag(tag)
                )
                .collect(Collectors.toList());
    }
}
