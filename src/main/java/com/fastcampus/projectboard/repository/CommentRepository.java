package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
