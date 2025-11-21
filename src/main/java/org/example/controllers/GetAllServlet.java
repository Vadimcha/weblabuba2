package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.example.error.ErrorResponse;
import org.example.models.History;

import java.io.IOException;

@WebServlet("/private/get-all")
public class GetAllServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            processRequest(req, resp);
        } catch (Exception e) {
            new ErrorResponse(e).send(resp);
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        History history = History.getInstance(req);
        req.setAttribute("history", history.getAll());
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
