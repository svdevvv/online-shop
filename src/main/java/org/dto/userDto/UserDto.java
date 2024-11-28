package org.dto.userDto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserDto {
    Integer id;
    String login;
}
