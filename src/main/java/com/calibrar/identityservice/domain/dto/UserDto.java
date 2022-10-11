package com.calibrar.identityservice.domain.dto;

import com.calibrar.identityservice.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    @Email(message = "Please input a valid email.")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotEmpty(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotEmpty(message = "Last name is required")

    private String lastName;

    @NotNull(message = "Birthday is required")
    private LocalDate birthDate;

    private Integer age;

    public UserDto convertToDto(User response) {

        return UserDto
                .builder()
                .email(response.getEmail())
                .firstName(response.getFirstName())
                .middleName(response.getMiddleName())
                .lastName(response.getLastName())
                .birthDate(response.getBirthDate())
                .password(response.getPassword())
                .age(response.getAge())
                .build();
    }

}
