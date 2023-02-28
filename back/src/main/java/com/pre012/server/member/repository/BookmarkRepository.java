package com.pre012.server.member.repository;

import com.pre012.server.member.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByMemberIdAndQuestionId(Long memberId, Long questionId);
}
