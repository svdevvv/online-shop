package org.webs.webFilters.rolePermissionFilter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dao.classes.roleDao.RoleDao;
import org.dao.classes.userDao.UserDao;
import org.dto.loginUserDto.LoginUserDto;
import org.entity.roleEntity.RoleEntity;
import org.util.urlPath.UrlPath;

import java.io.IOException;
import java.util.List;

import static org.util.urlPath.UrlPath.*;

@WebFilter("/admin")
public class RolePermissionFilter implements Filter {
    public static final RoleDao roleDao = RoleDao.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isAdminRole(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            var prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
            ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage : LOGIN);
        }
    }

    private boolean isAdminRole(ServletRequest servletRequest) {
        var role = (LoginUserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("role");
        return role != null && role.getRole().getRole().equals("Admin");
    }

}


