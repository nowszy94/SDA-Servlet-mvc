package com.szymon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    void handleGet(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
