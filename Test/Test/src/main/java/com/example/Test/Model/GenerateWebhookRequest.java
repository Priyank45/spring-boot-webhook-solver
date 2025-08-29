package com.example.Test.model;

public class GenerateWebhookRequest {
    private String name;
    private String regNo;
    private String email;

    // Constructors
    public GenerateWebhookRequest(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }

    // Getters & Setters
    public String getName() { return name; }
    public String getRegNo() { return regNo; }
    public String getEmail() { return email; }
}
