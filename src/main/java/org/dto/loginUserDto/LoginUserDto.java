package org.dto.loginUserDto;

import lombok.Builder;
import lombok.Value;
import org.entity.enums.gender.Gender;
import org.entity.roleEntity.RoleEntity;

import java.time.LocalDate;

@Builder
@Value
public class LoginUserDto {
    Integer id;
    String login;
    String firstName;
    String lastName;
    LocalDate birthday;
    String email;
    RoleEntity role;
    Gender gender;

}
