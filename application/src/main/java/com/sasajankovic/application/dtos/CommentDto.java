package com.sasajankovic.application.dtos;

import com.sasajankovic.domain.entities.comments.Comment;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String createdBy;

    public static CommentDto fromDomainEntity(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId().get())
                .content(comment.getContent().toString())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .createdBy(comment.getUser().getUsername())
                .build();
    }
}
