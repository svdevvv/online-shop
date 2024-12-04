package org.entity.blackListEntity;

import lombok.*;
import org.entity.roleEntity.RoleEntity;
import org.postgresql.util.PGInterval;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class BlackListEntity {
    private Integer id;
    private Integer userId;
    private String login;
    private RoleEntity role;
    private String reason;
    private String description;
    private LocalDateTime block_time;
    private PGInterval block_duration;
}
