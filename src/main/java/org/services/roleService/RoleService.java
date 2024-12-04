package org.services.roleService;

import org.dao.classes.roleDao.RoleDao;
import org.dto.roleDto.RoleDto;

import java.util.List;
import java.util.stream.Collectors;

public class RoleService {
    private static final RoleService INSTANCE = new RoleService();
    private final RoleDao roleDao = RoleDao.getInstance();

    private RoleService() {

    }

    public List<RoleDto> findAll() {
        return roleDao.findAll().stream()
                .map(roleEntity -> RoleDto.builder()
                        .roleName(roleEntity.getRole())
                        .build()).collect(Collectors.toList());
    }

    public static RoleService getInstance() {
        return INSTANCE;
    }
}
