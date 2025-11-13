package org.example.controllers;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String x = req.getParameter("x");
        String y = req.getParameter("y");
        String r = req.getParameter("r");

        if (x != null && y != null && r != null) {
            // есть данные → передаём в AreaCheckServlet
            RequestDispatcher dispatcher = req.getRequestDispatcher("/check");
            dispatcher.forward(req, resp);
        } else {
            // нет данных → показать форму
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
