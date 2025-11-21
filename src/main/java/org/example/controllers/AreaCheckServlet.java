package org.example.controllers;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.http.*;
import org.example.helpers.BatmanHitTest;
import org.example.helpers.Validator;
import org.example.models.History;
import org.example.models.RequestData;
import org.example.models.Result;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

@WebServlet("/private/area-check")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject json = new JsonObject();

        // Парсим параметры
        BigDecimal x = null, y = null, r = null;
        String from = req.getParameter("from");

        try {
            x = new BigDecimal(req.getParameter("x"));
            y = new BigDecimal(req.getParameter("y"));
            r = new BigDecimal(req.getParameter("r"));
        } catch (NumberFormatException e) {
            // Если парсинг не удался, вернём ошибку
            History history = History.getInstance(req);
            json.addProperty("status", "error");
            json.addProperty("error", "Некорректные числа");
            json.add("history", gson.toJsonTree(history.getAll()));
            resp.getWriter().write(gson.toJson(json));
            return;
        }

        RequestData data = new RequestData(x, y, r);

        // Валидация
        String validationError = Validator.validate(data);
        History history = History.getInstance(req);

        if (validationError != null) {
            json.addProperty("status", "error");
            json.addProperty("error", validationError);
            json.add("history", gson.toJsonTree(history.getAll()));
            resp.getWriter().write(gson.toJson(json));
            return;
        }

        // Всё ок — вычисляем попадание
        boolean hit = BatmanHitTest.isInBatman(x, y, r);
        String runtime = BatmanHitTest.getRunTime();

        Result result = new Result(runtime, hit, x, y, r,
                hit ? "Вы попали" : "Вы не попали", "ok", "");
        history.add(result);

        if(Objects.equals(from, "form")) {
            req.setAttribute("history", history);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/result.jsp");
            dispatcher.forward(req, resp);
        } else {
            json.addProperty("status", "get_table");
            json.add("history", gson.toJsonTree(history.getAll()));
            resp.getWriter().write(gson.toJson(json));
        }
    }
}
