package com.sasajankovic.application.controllers;

import com.sasajankovic.application.dtos.CreateUserDto;
import com.sasajankovic.application.dtos.CredentialsDto;
import com.sasajankovic.application.dtos.VerificationTokenDto;
import com.sasajankovic.domain.entities.user.Password;
import com.sasajankovic.domain.entities.user.User;
import com.sasajankovic.domain.entities.user.Username;
import com.sasajankovic.domain.exceptions.ConflictException;
import com.sasajankovic.domain.model.Credentials;
import com.sasajankovic.domain.model.Token;
import com.sasajankovic.domain.ports.in.LoginUserUseCase;
import com.sasajankovic.domain.ports.in.RegisterUserUserCase;
import com.sasajankovic.domain.ports.in.VerifyAccountUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.sasajankovic.application.utils.TestUtils.json;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @MockBean private LoginUserUseCase loginUserUseCase;
    @MockBean private RegisterUserUserCase registerUserUserCase;
    @MockBean private VerifyAccountUseCase verifyAccountUseCase;

    @Autowired private MockMvc mvc;

    @Test
    public void ShouldLoginWhenTheCredentialsAreGood() throws Exception {
        String token = UUID.randomUUID().toString();
        CredentialsDto requestBody = new CredentialsDto("username", "password");
        Credentials credentials =
                new Credentials(Username.of("username"), new Password("password"));
        Mockito.when(loginUserUseCase.login(credentials)).thenReturn(Token.create(token));

        mvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(token)));
    }

    @Test
    public void RequesterUserWhenTheUsernameIsTakenShouldReturnConflictStatusCode()
            throws Exception {
        Mockito.doThrow(new ConflictException("Username is already taken"))
                .when(registerUserUserCase)
                .registerUser(any(User.class));
        CreateUserDto requestBody =
                new CreateUserDto("John", "Smith", "johnsmith", "john@gmail.com", "Johnsmith!555");
        mvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(requestBody)))
                .andExpect(status().isConflict());
    }

    @Test
    public void RegisterUserShouldCreateNewUser() throws Exception {
        CreateUserDto user =
                new CreateUserDto(
                        "John", "Smith", "johnsmith", "johnsmith@gmail.com", "Johnsmith!123");

        mvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void VerifyAccountShouldActivateUser() throws Exception {
        VerificationTokenDto requestBody = new VerificationTokenDto(UUID.randomUUID().toString());

        mvc.perform(
                        post("/auth/account/verify")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json(requestBody)))
                .andExpect(status().isOk());
    }
}
