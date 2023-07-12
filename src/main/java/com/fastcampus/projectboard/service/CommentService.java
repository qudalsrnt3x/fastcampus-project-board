package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.Comment;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.dto.CommentDto;
import com.fastcampus.projectboard.repository.ArticleRepository;
import com.fastcampus.projectboard.repository.CommentRepository;
import com.fastcampus.projectboard.repository.UserAccountRepository;
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
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> searchArticleComment(Long articleId) {
        return commentRepository.findByArticle_Id(articleId).stream().map(CommentDto::from).toList();
    }

    public void saveComment(CommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            commentRepository.save(dto.toEntity(article, userAccount));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    /**
     * @deprecated 댓글 수정 기능은 클라이언트에서 생각할 점이 많아지기 떄문에, 이번 개발에서는 제공하지 않기로 했다.
     */
    @Deprecated
    public void updateComment(CommentDto dto) {
        try {
            Comment comment = commentRepository.getReferenceById(dto.id());
            if (dto.content() != null) comment.setContent(dto.content());
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    public void deleteComment(Long commentId, String userId) {
        commentRepository.deleteByIdAndUserAccount_UserId(commentId, userId);
    }
}
