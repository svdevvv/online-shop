package project.Runner;

import project.DAO.RoleDao;
import project.entity.RoleEntity;

public class DAORunner {
    public static void main(String[] args) {
        deleteTest();
//        saveTest();

    }

    public static void saveTest() {
        RoleDao roleDao = RoleDao.getInstance();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1);
        roleEntity.setRole("ADMIN");
        RoleEntity save = roleDao.save(roleEntity);
        System.out.println(save);
    }

    public static void deleteTest() {
        RoleDao roleDao = RoleDao.getInstance();
        boolean delete = roleDao.delete(1);
        System.out.println(delete);
    }
}
