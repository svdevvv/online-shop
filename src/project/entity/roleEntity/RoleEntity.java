package project.entity.roleEntity;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class RoleEntity {
    private Integer id;
    private String role;

}
