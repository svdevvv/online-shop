package org.services.classes.mappers.loginUserMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dto.loginUserDto.LoginUserDto;
import org.entity.userEntity.UserEntity;
import org.services.interfaces.mappers.mapper.Mapper;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class LoginUserMapper implements Mapper<UserEntity, LoginUserDto> {
    private static final LoginUserMapper INSTANCE = new LoginUserMapper();
    @Override
    public LoginUserDto mapFrom(UserEntity object) {
        return LoginUserDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .login(object.getLogin())
                .email(object.getEmail())
                .role(object.getRole())
                .birthday(object.getBirthday())
                .gender(object.getGender())
                .build();
    }
    public static LoginUserMapper getInstance() {
        return INSTANCE;
    }
}
