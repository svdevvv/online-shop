package org.webs.coockie.countEnteringServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/cookies")
public class CountEnteringServlet extends HttpServlet {
    private static final String UNIQUE_ID = "userId";
    private static final AtomicInteger counter = new AtomicInteger();
    private static final Integer COOKIE_LIFE_TIME = 900;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var cookies = req.getCookies();
        if(cookies == null || Arrays.stream(cookies)
                .filter(cookie -> UNIQUE_ID.equals(cookie.getName()))
                .findFirst().isEmpty()) {
            var cookie = new Cookie(UNIQUE_ID, "1");
            cookie.setPath("/cookies");
            cookie.setMaxAge(COOKIE_LIFE_TIME);
            resp.addCookie(cookie);
            counter.incrementAndGet();
        }
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        try (var writer = resp.getWriter()) {
            writer.write(counter.get());
        }

    }
}
