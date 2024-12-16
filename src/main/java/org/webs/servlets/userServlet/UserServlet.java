package org.webs.servlets.userServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.services.entityServices.findUserService.FindUserService;
import org.util.jspHelper.JspHelper;

import java.io.IOException;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final FindUserService findUserService = FindUserService.getInstance() ;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", findUserService.findAllUsers());

        req.getRequestDispatcher(JspHelper.getPath("users"))
                .forward(req, resp);
    }
}
