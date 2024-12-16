package org.dto.createUserDto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class CreateUserDto {
    String login;
    String password;
    String role;
    String firstName;
    String lastName;
    String email;
    LocalDate birthday;
    String gender;
}
