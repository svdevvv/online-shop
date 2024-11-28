package org.entity.userInfoEntity;

import lombok.*;
import org.entity.userEntity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserInfoEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private UserEntity user;
}
