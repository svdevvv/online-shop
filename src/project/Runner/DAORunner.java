package project.Runner;

import project.DAO.RoleDao;
import project.dto.filter.RoleFilter;
import project.entity.RoleEntity;

import java.util.List;

public class DAORunner {
    public static void main(String[] args) {
        RoleFilter admin = new RoleFilter(3, 0, "ADMIN");
        List<RoleEntity> all = RoleDao.getInstance().findAll(admin);
        System.out.println(all);



    }

    public static void saveTest() {
        RoleDao roleDao = RoleDao.getInstance();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(3);
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
