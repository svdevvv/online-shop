package project.Runner;

import project.daoClass.roleDao.RoleDao;
import project.daoClass.userDao.UserDao;
import project.dtoClass.filter.roleFilter.RoleFilter;
import project.entity.roleEntity.RoleEntity;
import project.entity.userEntity.UserEntity;

import javax.management.relation.Role;
import java.util.List;

public class DAORunner {
    public static void main(String[] args) {
        saveMethodForUser();
    }

    private static void saveMethodForUser() {
        UserDao userDao = UserDao.getInstance();

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("ABVS");

        userEntity.setPassword("pass");
        userEntity.setRole(new RoleEntity(1,"ADMIN"));

        UserEntity save = userDao.save(userEntity);
        System.out.println(save);
    }


    public static void saveTest() {
        RoleDao roleDao = RoleDao.getInstance();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(2);
        roleEntity.setRole("USER");

        RoleEntity save = roleDao.save(roleEntity);
        System.out.println(save);
    }

    private static void filterTest() {
        RoleFilter admin = new RoleFilter(3, 0, "ADMIN");
        List<RoleEntity> all = RoleDao.getInstance().findAll(admin);
        System.out.println(all);
    }
    public static void deleteForUser(){
        UserDao userDao = UserDao.getInstance();
        boolean delete = userDao.delete(17);
        System.out.println(delete);
    }

    public static void deleteTest() {
        RoleDao roleDao = RoleDao.getInstance();
        boolean delete = roleDao.delete(1);
        System.out.println(delete);
    }
}
