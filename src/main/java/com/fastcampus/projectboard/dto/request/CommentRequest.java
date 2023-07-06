package com.fastcampus.projectboard.dto.request;

import com.fastcampus.projectboard.dto.CommentDto;
import com.fastcampus.projectboard.dto.UserAccountDto;

public record CommentRequest(Long articleId, String content) {

    public static CommentRequest of(Long articleId, String content) {
        return new CommentRequest(articleId, content);
    }

    public CommentDto toDto(UserAccountDto userAccountDto) {
        return CommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}
