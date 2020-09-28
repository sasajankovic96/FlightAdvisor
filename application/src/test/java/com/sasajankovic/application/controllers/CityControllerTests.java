package com.sasajankovic.application.controllers;

import com.sasajankovic.application.dtos.CommentContentDto;
import com.sasajankovic.application.dtos.SearchCityParamsDto;
import com.sasajankovic.application.utils.TestUtils;
import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.model.SearchCityParams;
import com.sasajankovic.domain.ports.in.FetchCitiesUseCase;
import com.sasajankovic.domain.ports.in.PostCommentUseCase;
import com.sasajankovic.domain.ports.in.SearchCitiesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sasajankovic.application.utils.TestUtils.createCity;
import static com.sasajankovic.application.utils.TestUtils.json;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerTests {

    @MockBean private FetchCitiesUseCase fetchCitiesUseCase;

    @MockBean private PostCommentUseCase postCommentUseCase;

    @MockBean private SearchCitiesUseCase searchCitiesUseCase;

    @Autowired private MockMvc mvc;

    private List<City> cities;

    @BeforeEach
    public void setup() {
        cities =
                Arrays.asList(
                        createCity(
                                1l,
                                "Belgrade",
                                "The capitol of Serbia",
                                "Serbia",
                                Collections.emptyList()),
                        createCity(
                                2l,
                                "Berlin",
                                "The capitol of Germany",
                                "Germany",
                                Collections.emptyList()));
    }

    @Test
    public void PostingACommentNotLoggedInShouldReturnUnauthorized() throws Exception {
        CommentContentDto requestBody = new CommentContentDto("Comment content");
        mvc.perform(
                        post("/cities/1/comments")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(requestBody)))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "dummyUser", roles = "ADMINISTRATOR")
    @Test
    public void PostingACommentNotRegularUserShouldReturnForbidden() throws Exception {
        CommentContentDto requestBody = new CommentContentDto("Comment content");
        mvc.perform(
                        post("/cities/1/comments")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(requestBody)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "dummyUser", roles = "REGULAR_USER")
    @Test
    public void PostCommentShouldCreateANewComment() throws Exception {
        CommentContentDto content = new CommentContentDto("Comment content");
        mvc.perform(
                        post("/cities/1/comments")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(content)))
                .andExpect(status().isCreated());
    }

    @Test
    public void FetchingCitiesNotLoggedInShouldReturnUnauthorized() throws Exception {
        mvc.perform(get("/cities")).andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "dummyUser", roles = "REGULAR_USER")
    @Test
    public void FetchCitiesShouldReturnCities() throws Exception {
        Mockito.when(fetchCitiesUseCase.fetchCitiesWithComments(Optional.empty()))
                .thenReturn(cities);

        mvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Belgrade")))
                .andExpect(jsonPath("$[0].country", is("Serbia")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Berlin")))
                .andExpect(jsonPath("$[1].country", is("Germany")));
    }

    @Test
    public void SearchCitiesNotLoggedInShouldReturnUnauthorized() throws Exception {
        mvc.perform(
                        post("/cities/search")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(new SearchCityParamsDto("Berlin", null))))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "dummyUser", roles = "REGULAR_USER")
    @Test
    public void SearchCitiesShouldReturnCities() throws Exception {
        SearchCityParamsDto requestBody = new SearchCityParamsDto("Berlin", 2);
        Mockito.when(
                        searchCitiesUseCase.searchCities(
                                new SearchCityParams("Berlin", Optional.of(2))))
                .thenReturn(cities);

        mvc.perform(
                        post("/cities/search")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(TestUtils.json(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Belgrade")))
                .andExpect(jsonPath("$[0].country", is("Serbia")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Berlin")))
                .andExpect(jsonPath("$[1].country", is("Germany")));
    }
}
