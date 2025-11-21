package org.example.models;

public enum RequestType {
    CALCULATE("calculate"),
    GET_ALL("get-all"),
    CLEAR("clear");

    public final String jsonValue;

    RequestType(String jsonValue) {
        this.jsonValue = jsonValue;
    }
}
