package org.example;

public enum StatusResponse {

    SUCCESS ("Success"),
    ERROR ("Error");

    public final String label;

    private StatusResponse(String label) {
        this.label = label;
    }
}
