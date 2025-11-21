package org.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class MainFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        System.out.println("FILTER: incoming " + req.getRequestURI());

        chain.doFilter(request, response);

        System.out.println("FILTER: response ready " + req.getRequestURI());
    }
}

// "/*" "/api/*" "/api/..."