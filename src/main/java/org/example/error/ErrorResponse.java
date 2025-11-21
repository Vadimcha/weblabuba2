package org.example.error;

import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    public String message;
    public String stackTrace;

    public ErrorResponse(Throwable error) {
        this.message = error.getMessage();
        this.stackTrace = Arrays.toString(error.getStackTrace());
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    public void send(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        body.put("stackTrace", stackTrace);

        response.getWriter().write(mapper.writeValueAsString(body));
    }
}

