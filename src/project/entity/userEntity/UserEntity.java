package project.entity.userEntity;

import lombok.*;
import project.entity.roleEntity.RoleEntity;

import javax.management.relation.Role;

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
