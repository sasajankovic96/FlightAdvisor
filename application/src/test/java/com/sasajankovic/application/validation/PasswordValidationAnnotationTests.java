package com.sasajankovic.application.validation;

import com.sasajankovic.application.dtos.CreateUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@SpringBootTest
public class PasswordValidationAnnotationTests {

    @Autowired private Validator validator;

    @ParameterizedTest
    @ValueSource(
            strings = {
                "john.smith",
                "j!12G",
                "johnSmith123",
                "john",
                "JohnSmith!",
                "johnsmith!123"
            })
    public void ShouldFailToValidateThePassword(String password) {
        CreateUserDto user =
                new CreateUserDto("John", "Smith", "john!smith", "john@gmail.com", password);

        Set<ConstraintViolation<CreateUserDto>> violations = validator.validate(user);

        Assertions.assertEquals(1, violations.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"JohnSmith!123", "jackWhite@56", "RegPass23%"})
    public void ShouldSuccessfullyValidateThePassword(String password) {
        CreateUserDto user =
                new CreateUserDto("John", "Smith", "john!smith", "john@gmail.com", password);

        Set<ConstraintViolation<CreateUserDto>> violations = validator.validate(user);

        Assertions.assertEquals(0, violations.size());
    }
}
