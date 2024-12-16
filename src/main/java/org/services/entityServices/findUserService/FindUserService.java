package org.services.entityServices.findUserService;

import org.dao.classes.userDao.UserDao;
import org.dto.createUserDto.CreateUserDto;

import java.util.List;

public class FindUserService {
    private static FindUserService INSTANCE;
    private final UserDao userDao = UserDao.getInstance();


    private FindUserService() {

    }

    public List<CreateUserDto> findAllUsers() {
        return userDao.findAll().stream()
                .map(userEntity -> CreateUserDto.builder()
//                        .id(userEntity.getId())
                        .login(userEntity.getLogin())
//                        .role(String.valueOf(userEntity.getRole().getRole()))
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .email(userEntity.getEmail())
                        .birthday(userEntity.getBirthday())
                        .gender(String.valueOf(userEntity.getGender()))
                        .build()).toList();
    }
    public static FindUserService getInstance(){
        if(INSTANCE == null){
            return new FindUserService();
        }
        return INSTANCE;
    }
}
