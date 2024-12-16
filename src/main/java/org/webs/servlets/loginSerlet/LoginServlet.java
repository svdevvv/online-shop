package org.webs.servlets.loginSerlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.dto.loginUserDto.LoginUserDto;
import org.services.service.userService.UserService;
import org.util.jspHelper.JspHelper;
import org.util.urlPath.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {
    private static final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("login"),req.getParameter("password"))
                .ifPresentOrElse(
                        user-> onLoginSuccess(user,req,resp),
                        () -> onLoginFail(req, resp)
                );
    }
@SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login?error&login=" + req.getParameter("login"));
    }

    @SneakyThrows
    private void onLoginSuccess(LoginUserDto user, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/main");
    }
}
