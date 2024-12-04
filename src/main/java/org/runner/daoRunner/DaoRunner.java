package org.runner.daoRunner;

import org.dao.classes.blackListDao.BlackListDao;
import org.dao.classes.productDao.ProductDao;
import org.dao.classes.roleDao.RoleDao;
import org.dao.classes.userDao.UserDao;
import org.dao.classes.userInfoDao.UserInfoDao;
import org.entity.blackListEntity.BlackListEntity;
import org.entity.productEntity.ProductEntity;
import org.postgresql.util.PGInterval;
import org.services.filters.roleFilter.RoleFilter;
import org.services.filters.userFilter.UserFilter;
import org.entity.roleEntity.RoleEntity;
import org.entity.userEntity.UserEntity;
import org.entity.userInfoEntity.UserInfoEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DaoRunner {
    private static final RoleDao ROLE_DAO = RoleDao.getInstance();
    private static final UserDao USER_DAO = UserDao.getInstance();
    private static final UserInfoDao USER_INFO_DAO = UserInfoDao.getInstance();
    private static final BlackListDao BLACK_LIST_DAO = BlackListDao.getInstance();
    private static final ProductDao PRODUCT_DAO = ProductDao.getInstance();


    public static void main(String[] args) throws SQLException {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Bike");
        productEntity.setDescription("Mountain bike for everyone!");
        productEntity.setPrice(BigDecimal.valueOf(270.50));

        var save = PRODUCT_DAO.save(productEntity);
        System.out.println(save);
    }

    public static void saveForRole() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(3);
        roleEntity.setRole("Ghost");

        RoleEntity save = ROLE_DAO.save(roleEntity);
        System.out.println(save);
    }

    private static void saveForBlackList() throws SQLException {
        List<UserEntity> users = USER_DAO.findAll();
        UserEntity userEntity = users.isEmpty() ? users.get(2) : users.getFirst();
        List<RoleEntity> roles = ROLE_DAO.findAll();
        RoleEntity roleEntity = roles.isEmpty() ? roles.getLast() : roles.get(1);
        PGInterval interval = new PGInterval("2 hours 3             0 minutes");


        System.out.println(userEntity);
        if (userEntity != null) {
            BlackListEntity blackListEntity = new BlackListEntity();
            blackListEntity.setId(blackListEntity.getId());
            blackListEntity.setUserId(userEntity.getId());
            blackListEntity.setLogin(userEntity.getLogin());
            blackListEntity.setReason("Spam");
            blackListEntity.setDescription("Spam same text 5 times");
            blackListEntity.setRole(userEntity.getRole());
            blackListEntity.setBlock_time(LocalDateTime.now());
            blackListEntity.setBlock_duration(interval);

            BlackListEntity save = BLACK_LIST_DAO.save(blackListEntity);
            System.out.println(save);

        }
    }

    private static void findAllForBlackList() {
        BlackListDao blackListDao = BlackListDao.getInstance();
        var all = blackListDao.findAll();
        System.out.println(all);
    }

    private static void findAllForUserInfo() {
        UserInfoDao userInfoDao = UserInfoDao.getInstance();
        var all = userInfoDao.findAll();
        System.out.println(all);
    }

    private static void saveForUserInfo() {
        List<UserEntity> users = USER_DAO.findAll();
        UserEntity userEntity = users.isEmpty() ? null : users.getFirst();


        System.out.println(userEntity);
        if (userEntity != null) {
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setId(userEntity.getId());
            userInfoEntity.setFirstName("Nikita");
            userInfoEntity.setLastName("pavlov");
            userInfoEntity.setAddress("Moscow, suvorov 25");
            userInfoEntity.setUser(userEntity);

            UserInfoEntity save = USER_INFO_DAO.save(userInfoEntity);
            System.out.println(save);
        }
    }

    public static void saveForUser() {
        List<RoleEntity> roles = ROLE_DAO.findAll();
        RoleEntity roleEntity = roles.isEmpty() ? roles.getLast() : roles.get(1);

        System.out.println(roleEntity);
        if (roleEntity != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setLogin("Bobka");

            userEntity.setPassword("krisss");
            userEntity.setRole(roleEntity);

            UserEntity save = USER_DAO.save(userEntity);
            System.out.println(save);
        }
    }

    public static void deleteForUser() {
        UserDao userDao = UserDao.getInstance();
        boolean delete = userDao.delete(5);
        System.out.println(delete);
    }


    public static void findByIdForRole() {
        Optional<RoleEntity> findById = ROLE_DAO.findById(1);
        System.out.println(findById);
    }

    public static void findAllForUser() {
        List<UserEntity> findAll = USER_DAO.findAll();
        System.out.println(findAll);
    }

    public static void findAllForRole() {
        List<RoleEntity> findAll = ROLE_DAO.findAll();
        System.out.println(findAll);
    }

    private static void findByFilterForRole() {
        RoleFilter user = new RoleFilter(3, 0, "USER");
        List<RoleEntity> all = ROLE_DAO.findAll(user);
        System.out.println(all);
    }

    private static void findByFilterForUser() {
        UserFilter user = new UserFilter(3, 0, "ADMIN");
        List<UserEntity> all = USER_DAO.findAll(user);
        System.out.println(all);
    }

    public static void deleteForRole() {
        boolean delete = ROLE_DAO.delete(1);
        System.out.println(delete);
    }
}