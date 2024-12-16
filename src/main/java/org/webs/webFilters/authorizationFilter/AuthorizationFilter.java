package org.webs.webFilters.authorizationFilter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dto.loginUserDto.LoginUserDto;

import java.io.IOException;
import java.util.Set;

import static org.util.urlPath.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN,REGISTRATION);
    private static final Set<String> PRIVATE_PATH = Set.of();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if(isPublicPath(uri) || isUserLoggedIn(servletRequest)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            var prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
            ((HttpServletResponse)servletResponse).sendRedirect(prevPage != null ? prevPage : LOGIN);
        }
    }


    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = (LoginUserDto)((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user!=null;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }
}
