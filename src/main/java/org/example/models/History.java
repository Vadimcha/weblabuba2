package org.example.models;

import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History implements Serializable {

    private final List<Result> results = new ArrayList<>();

    public void add(Result result) {
        results.add(0, result);
    }

    public List<Result> getAll() {
        return Collections.unmodifiableList(results);
    }

    public void clear() {
        results.clear();
    }

    public static History getInstance(HttpServletRequest request) {
        History history = (History) request.getSession().getAttribute("history");

        if (history == null) {
            history = new History();
            request.getSession().setAttribute("history", history);
        }

        return history;
    }
}
