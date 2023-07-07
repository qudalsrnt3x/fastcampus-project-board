package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.dto.request.ArticleRequest;
import com.fastcampus.projectboard.dto.request.CommentRequest;
import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import com.fastcampus.projectboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/new")
    public String postNewArticleComment(CommentRequest commentRequest,
                                        @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        commentService.saveComment(commentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + commentRequest.articleId();
    }

    @PostMapping ("/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, Long articleId,
                                @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        commentService.deleteComment(commentId, boardPrincipal.getUsername());

        return "redirect:/articles/" + articleId;
    }
}
