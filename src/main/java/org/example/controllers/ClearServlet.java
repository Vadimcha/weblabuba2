package org.example.controllers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.example.error.ErrorResponse;
import org.example.models.History;

import java.io.IOException;

@WebServlet("/private/clear")
public class ClearServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            processRequest(req, resp);
        } catch (Exception e) {
            new ErrorResponse(e).send(resp);
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        History history = History.getInstance(req);
        history.clear();

        Gson gson = new Gson();

        JsonObject json = new JsonObject();
        json.addProperty("status", "clear");
        json.add("history", gson.toJsonTree(history.getAll()));

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(json));
    }

}
