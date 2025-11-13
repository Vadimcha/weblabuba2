package org.example.controllers;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.helpers.Calculator;
import org.example.helpers.Validator;
import org.example.models.History;
import org.example.models.RequestData;
import org.example.models.Result;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            BigDecimal x = new BigDecimal(req.getParameter("x"));
            BigDecimal y = new BigDecimal(req.getParameter("y"));
            BigDecimal r = new BigDecimal(req.getParameter("r"));

            RequestData data = new RequestData(x, y, r);
            Validator.validate(data);

            boolean hit = Calculator.calculate(data.toPoint());
            String runtime = Calculator.getRunTime();

            Result result = new Result(
                    runtime, hit, x, y, r,
                    hit ? "Вы попали" : "Вы не попали",
                    "ok", ""
            );

            // сохраняем историю в сессию
            HttpSession session = req.getSession();
            History history = (History) session.getAttribute("history");
            if (history == null) {
                history = new History();
                session.setAttribute("history", history);
            }
            history.add(result);

            req.setAttribute("result", result);
            req.setAttribute("history", history.getAll());

            RequestDispatcher dispatcher = req.getRequestDispatcher("/result.jsp");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
