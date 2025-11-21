package org.example.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/api/*")
public class ApiRouterFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String type = req.getParameter("requestType");

        if ("calculate".equals(type)) {
            req.getRequestDispatcher("/private/area-check").forward(req, response);
            return;
        }

        if ("clear".equals(type)) {
            req.getRequestDispatcher("/private/clear").forward(req, response);
            return;
        }

        if ("get-all".equals(type)) {
            req.getRequestDispatcher("/private/get-all").forward(req, response);
            return;
        }

        // иначе → продолжаем обычный ход
        chain.doFilter(request, response);
    }
}

