package com.fastcampus.projectboard.dto.response;

import com.fastcampus.projectboard.dto.CommentDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId
) implements Serializable {

    public static CommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
        return new CommentResponse(id, content, createdAt, email, nickname, userId);
    }

    public static CommentResponse from(CommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new CommentResponse(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.userAccountDto().userId()
        );
    }

}