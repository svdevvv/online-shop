package org.webs.servlets.registrationServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dto.createUserDto.CreateUserDto;
import org.exceptions.validationException.ValidationException;
import org.services.service.userService.UserService;
import org.util.jspHelper.JspHelper;
import org.util.urlPath.UrlPath;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(UrlPath.REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("genders", List.of("MALE", "FEMALE"));
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = CreateUserDto.builder()
                .login(req.getParameter("login"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .birthday(LocalDate.parse(req.getParameter("birthday")))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();

        try{
            userService.create(userDto);
            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getMessage());
            doGet(req, resp);
        }
    }
}
