package com.sasajankovic.application.dtos;

import com.sasajankovic.domain.entities.user.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserDto {
    @NotBlank(message = "First name must not be empty")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    private String lastName;

    @NotBlank(message = "Username must not be empty")
    @Length(min = 5, message = "Username must be at least 5 characters long")
    private String username;

    @javax.validation.constraints.Email(message = "Email must have valid format")
    @NotBlank(message = "Email must not be empty")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @com.sasajankovic.application.validation.Password
    private String password;

    public User toNewDomainEntity() {
        return User.createNewUser(
                new FirstName(firstName),
                new LastName(lastName),
                new Email(email),
                Username.of(username),
                new Password(password));
    }
}
