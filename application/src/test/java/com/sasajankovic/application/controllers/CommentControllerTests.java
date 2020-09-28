package com.sasajankovic.application.controllers;

import com.sasajankovic.application.dtos.CommentContentDto;
import com.sasajankovic.domain.ports.in.DeleteCommentUseCase;
import com.sasajankovic.domain.ports.in.UpdateCommentUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.sasajankovic.application.utils.TestUtils.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTests {
    @MockBean private UpdateCommentUseCase updateCommentUseCase;

    @MockBean private DeleteCommentUseCase deleteCommentUseCase;

    @Autowired private MockMvc mvc;

    @WithMockUser(value = "dummyUser", roles = "REGULAR_USER")
    @Test
    public void UpdateCommentShouldCompleteSuccessfully() throws Exception {
        Long commentId = 1l;
        CommentContentDto requestBody = new CommentContentDto("Updated comment content");

        mvc.perform(
                        put(String.format("/comments/%d", commentId))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdateCommentShouldReturnUnauthorizedWhenTheUserIsNotLoggedIn() throws Exception {
        Long commentId = 1l;
        CommentContentDto requestBody = new CommentContentDto("Updated comment content");

        mvc.perform(
                        put(String.format("/comments/%d", commentId))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(requestBody)))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "dummyUser", roles = "ADMINISTRATOR")
    @Test
    public void UpdateCommentShouldReturnForbiddenWhenTheUserIsNotRegularUser() throws Exception {
        Long commentId = 1l;
        CommentContentDto requestBody = new CommentContentDto("Updated comment content");

        mvc.perform(
                        put(String.format("/comments/%d", commentId))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(requestBody)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "dummyUser", roles = "REGULAR_USER")
    @Test
    public void DeleteCommentShouldCompleteSuccessfully() throws Exception {
        Long commentId = 1l;

        mvc.perform(delete(String.format("/comments/%d", commentId)))
                .andExpect(status().isNoContent());
    }
}
