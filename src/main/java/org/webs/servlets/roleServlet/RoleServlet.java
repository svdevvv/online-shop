package org.webs.servlets.roleServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.services.roleService.RoleService;

import java.io.IOException;

@WebServlet("/roles")
public class RoleServlet extends HttpServlet {
    private final RoleService roleService = RoleService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        try (var printWriter = resp.getWriter()) {
            printWriter.write("<h1>^_^:(</h1>");
            printWriter.write("<ul>");
//            roleService.findAll().forEach(roleDto -> {
//                printWriter.write("""
//                        <li>
//                            %s
//                        </li>
//                        """.formatted(roleDto.getRoleName()));
//            });
            printWriter.write("</ul>");
        }
    }
}
