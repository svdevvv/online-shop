package org.entity.userEntity;

import lombok.*;
import org.entity.roleEntity.RoleEntity;

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


}
