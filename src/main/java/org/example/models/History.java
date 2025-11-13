package org.example.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History {
    private final List<Result> results = new ArrayList<>();
    public void add(Result result) {
        Collections.reverse(results);
        results.add(result);
        Collections.reverse(results);
    }
    public List<Result> getAll() {
        return results;
    }
    public void clear() {
        results.clear();
    }
}