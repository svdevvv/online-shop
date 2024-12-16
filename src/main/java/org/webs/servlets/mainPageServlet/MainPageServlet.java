package org.webs.servlets.mainPageServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.util.jspHelper.JspHelper;
import org.util.urlPath.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.MAIN_PAGE)
public class MainPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getParameter("user");
        req.getRequestDispatcher(JspHelper.getPath("main")).forward(req, resp);
    }
}
