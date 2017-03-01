package com.szymon.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginController implements Controller {
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        PrintWriter writer = response.getWriter();
        writer.write("<h1>Hello From Login Controller</h1>");
        writer.write("<ul>");
        for (int i = 0; i < cookies.length; i++) {
            writer.write("<li>" + cookies[i].getName() + ": " + cookies[i].getValue() + "</li>");
        }
        writer.write("</ul>");
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("hello")) {
                Cookie cookie = cookies[i];
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
}
