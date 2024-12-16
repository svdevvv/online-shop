package org.services.service.userService;


import org.dao.classes.userDao.UserDao;
import org.dto.createUserDto.CreateUserDto;
import org.dto.loginUserDto.LoginUserDto;
import org.exceptions.validationException.ValidationException;
import org.services.classes.mappers.createUserMapper.CreateUserMapper;
import org.services.classes.mappers.loginUserMapper.LoginUserMapper;
import org.services.validators.classes.createUserValidator.CreateUserValidator;
import org.services.validators.classes.emailValidator.LoginValidator;

import java.util.Optional;

public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final LoginUserMapper loginUserMapper = LoginUserMapper.getInstance();
    private final LoginValidator emailValidator = LoginValidator.getInstance();

    public Optional<LoginUserDto> login(String login, String password) {
        return userDao.findByLoginAndPassword(login, password)
                .map(loginUserMapper::mapFrom);
    }

    public boolean checkEmail(String email) {
        var emailValid = emailValidator.isEmailValid(email);
        if (emailValid) {
            return true;
        } else {
            return false;
        }

    }

    public Integer create(CreateUserDto userDto) {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            System.out.println("Validation Failed!");
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMapper.mapFrom(userDto);
        userDao.save(userEntity);

        return userEntity.getId();

    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
