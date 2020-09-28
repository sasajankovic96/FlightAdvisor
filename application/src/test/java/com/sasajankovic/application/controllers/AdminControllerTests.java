package com.sasajankovic.application.controllers;

import com.sasajankovic.application.dtos.CityDto;
import com.sasajankovic.domain.entities.city.City;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.ports.in.CreateCityUseCase;
import com.sasajankovic.domain.ports.in.FetchCityByIdUseCase;
import com.sasajankovic.domain.ports.in.ImportDataUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.sasajankovic.application.utils.TestUtils.createCity;
import static com.sasajankovic.application.utils.TestUtils.json;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTests {
    @MockBean private CreateCityUseCase createCityUseCase;

    @MockBean private FetchCityByIdUseCase fetchCityByIdUseCase;

    @MockBean private ImportDataUseCase importDataUseCase;

    @Autowired private MockMvc mvc;

    @Test
    public void ImportDataNotLoggedInShouldReturnUnAuthorized() throws Exception {
        mvc.perform(post("/admin/data/import")).andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "dummyUser", roles = "REGULAR_USER")
    @Test
    public void ImportDataNotAdminShouldReturnForbidden() throws Exception {
        mvc.perform(post("/admin/data/import")).andExpect(status().isForbidden());
    }

    @WithMockUser(value = "dummyAdminUser", roles = "ADMINISTRATOR")
    @Test
    public void ImportDataShouldImportData() throws Exception {
        mvc.perform(post("/admin/data/import")).andExpect(status().isCreated());
    }

    @WithMockUser(value = "dummyAdminUser", roles = "ADMINISTRATOR")
    @Test
    public void CreateCityShouldCreateNewCity() throws Exception {
        Long createdCityId = 1l;
        CityDto requestBody =
                new CityDto(null, "Sabac", "Small Paris", "Serbia", Collections.emptyList());
        Mockito.when(createCityUseCase.createCity(Mockito.any(City.class)))
                .thenReturn(new CityId(createdCityId));

        mvc.perform(
                        post("/admin/cities")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(requestBody)))
                .andExpect(
                        header().string(
                                        HttpHeaders.LOCATION,
                                        containsString(
                                                String.format("/admin/cities/%d", createdCityId))))
                .andExpect(status().isCreated());
    }

    @WithMockUser(value = "dummyAdminUser", roles = "ADMINISTRATOR")
    @Test
    public void FetchCityByIdShouldReturnCity() throws Exception {
        Long cityId = 1l;
        City city = createCity(cityId, "Sabac", "Small Paris", "Serbia", Collections.emptyList());
        Mockito.when(fetchCityByIdUseCase.fetchById(new CityId(cityId))).thenReturn(city);

        mvc.perform(get(String.format("/admin/cities/%d", cityId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Sabac")))
                .andExpect(jsonPath("$.country", is("Serbia")));
    }

    @WithMockUser(value = "dummyAdminUser", roles = "ADMINISTRATOR")
    @Test
    public void FetchCityByIdShouldReturnNotFoundWhenTheCityDoesNotExist() throws Exception {
        Long cityId = 1l;
        Mockito.when(fetchCityByIdUseCase.fetchById(new CityId(cityId)))
                .thenThrow(EntityNotFoundException.class);

        mvc.perform(get(String.format("/admin/cities/%d", cityId)))
                .andExpect(status().isNotFound());
    }
}
