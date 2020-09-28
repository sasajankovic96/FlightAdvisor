package com.sasajankovic.application.controllers;

import com.sasajankovic.application.dtos.CommentContentDto;
import com.sasajankovic.domain.entities.comments.CommentContent;
import com.sasajankovic.domain.entities.comments.CommentId;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.ports.in.DeleteCommentUseCase;
import com.sasajankovic.domain.ports.in.UpdateCommentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("comments")
@AllArgsConstructor
@PreAuthorize("hasRole('REGULAR_USER')")
public class CommentController {
    private final UpdateCommentUseCase updateCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateComment(
            @RequestBody @Valid CommentContentDto content,
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        updateCommentUseCase.updateComment(
                new CommentId(id), new CommentContent(content.getContent()), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long id, @AuthenticationPrincipal User user) {
        deleteCommentUseCase.deleteComment(new CommentId(id), user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
