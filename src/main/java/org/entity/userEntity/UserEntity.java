package org.entity.userEntity;

import lombok.*;
import org.entity.enums.gender.Gender;
import org.entity.roleEntity.RoleEntity;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserEntity {
    private Integer id;
    private String login;
    private String password;
    private RoleEntity role;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private Gender gender;


}
