package org.services.classes.mappers.createUserMapper;

import org.dao.classes.roleDao.RoleDao;
import org.dto.createUserDto.CreateUserDto;
import org.entity.enums.gender.Gender;

import org.entity.userEntity.UserEntity;

import org.services.interfaces.mappers.mapper.Mapper;
import org.util.formatters.localDateFormatter.LocalDateFormatter;





public class CreateUserMapper implements Mapper<CreateUserDto, UserEntity>{
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    private static final RoleDao roleDao = RoleDao.getInstance();
    private final String user = "User";

    @Override
    public UserEntity mapFrom(CreateUserDto object) {
        var roles = roleDao.findAll();
        var roleEntity = roles.stream()
                .filter(role -> user.equals(role.getRole()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Роль с именем " + object.getRole() + " не найдена"));


        return UserEntity.builder()
                .login(object.getLogin())
                .email(object.getEmail())
                .password(object.getPassword())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .birthday(LocalDateFormatter.format(String.valueOf(object.getBirthday())))
                .role(roleEntity)
                .gender(Gender.valueOf(object.getGender()))
                .build();
    }
    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
