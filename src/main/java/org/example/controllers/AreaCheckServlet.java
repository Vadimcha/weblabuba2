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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            BigDecimal x = new BigDecimal(req.getParameter("x"));
            BigDecimal y = new BigDecimal(req.getParameter("y"));
            BigDecimal r = new BigDecimal(req.getParameter("r"));

            System.out.println("AreaCheckServlet received request: x=" + x + ", y=" + y + ", r=" + r);

            RequestData data = new RequestData(x, y, r);
            Validator.validate(data);

            boolean hit = Calculator.calculate(data.toPoint());
            String runtime = Calculator.getRunTime();

            Result result = new Result(
                    runtime, hit, x, y, r,
                    hit ? "Вы попали" : "Вы не попали",
                    "ok", ""
            );

            // сохраняем историю и результат в сессию
            HttpSession session = req.getSession();
            History history = (History) session.getAttribute("history");
            if (history == null) {
                history = new History();
                session.setAttribute("history", history);
            }
            history.add(result);
            session.setAttribute("result", result);

            resp.sendRedirect(req.getContextPath() + "/result.jsp");
        } catch (Exception e) {
            HttpSession session = req.getSession();
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
