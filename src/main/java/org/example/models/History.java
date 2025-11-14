package org.example.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History {
    private final List<Result> results = new ArrayList<>();
    public void add(Result result) {
        results.add(0, result);
    }
    public List<Result> getAll() {
        return results;
    }
    public void clear() {
        results.clear();
    }

    @Override
    public String toString() {
        if (results.isEmpty()) {
            return "История пуста";
        }

        StringBuilder sb = new StringBuilder("История результатов:\n");
        for (Result r : results) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }
}