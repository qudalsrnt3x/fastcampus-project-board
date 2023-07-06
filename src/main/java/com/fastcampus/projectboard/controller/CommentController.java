package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.dto.request.ArticleRequest;
import com.fastcampus.projectboard.dto.request.CommentRequest;
import com.fastcampus.projectboard.service.CommentService;
import lombok.RequiredArgsConstructor;
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
    public String postNewArticleComment(CommentRequest commentRequest) {

        // TODO: 인증 정보를 넣어줘야 한다.
        commentService.saveComment(commentRequest.toDto(UserAccountDto.of(
                "uno", "pw", "uno@mail.com", null, null
        )));

        return "redirect:/articles/" + commentRequest.articleId();
    }

    @PostMapping ("/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, Long articleId) {

        commentService.deleteComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
