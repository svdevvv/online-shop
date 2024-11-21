package project.dtoClass.filter.roleFilter;

import lombok.*;

import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class RoleFilter {
    private int limit;
    private int offset;
    private String role;

}
