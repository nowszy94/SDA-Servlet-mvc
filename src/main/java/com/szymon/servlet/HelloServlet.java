package com.szymon.servlet;

import com.szymon.controller.Controller;
import com.szymon.controller.CookieController;
import com.szymon.controller.LoginController;
import com.szymon.controller.UserController;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import pl.sda.file.FileOperations;
import pl.sda.file.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HelloServlet extends HttpServlet {

    private Map<String, Controller> controllerMap;

    @Override
    public void init() throws ServletException {
        this.controllerMap = new HashMap<String, Controller>();
        this.controllerMap.put("users", new UserController());
        this.controllerMap.put("login", new LoginController());
        this.controllerMap.put("cookie", new CookieController());
        this.controllerMap.put("default", (request, response) ->
                response.getWriter().write("<h1>Hello from default controller</h1>"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String requestURI = req.getRequestURI();
        String relativePath = requestURI.substring(StringUtils.ordinalIndexOf(requestURI, "/", 2) + 1);
        Controller controller = controllerMap.get(relativePath);
        if (controller == null) {
            controller = controllerMap.get("default");
        }
        controller.handleGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String age = req.getParameter("age");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(new Integer(age));

        File file = new File("C:\\Users\\Szymon\\Desktop\\test.txt");
        FileOperations.addUserToFile(user, file);
    }

    private void raw(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String body = reader.readLine();
        String[] split = body.split(" ");

        User user = new User();
        user.setFirstName(split[0]);
        user.setLastName(split[1]);
        user.setAge(new Integer(split[2]));

        File file = new File("C:\\Users\\Szymon\\Desktop\\test.txt");
        FileOperations.addUserToFile(user, file);

        resp.getWriter().write("Wszystko ok");
        resp.setStatus(201);
    }
}
