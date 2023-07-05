package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.Comment;
import com.fastcampus.projectboard.dto.CommentDto;
import com.fastcampus.projectboard.repository.ArticleRepository;
import com.fastcampus.projectboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> searchArticleComment(Long articleId) {
        return commentRepository.findByArticle_Id(articleId).stream().map(CommentDto::from).toList();
    }

    public void saveComment(CommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            commentRepository.save(dto.toEntity(article));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글의 게시글을 찾을 수 없습니다. - dto {}", dto);
        }
    }

    public void updateComment(CommentDto dto) {
        try {
            Comment comment = commentRepository.getReferenceById(dto.id());
            if (dto.content() != null) comment.setContent(dto.content());
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
