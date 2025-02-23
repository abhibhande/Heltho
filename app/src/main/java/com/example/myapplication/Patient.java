package com.example.myapplication;

public class Patient {
    private String name;
    private String disease;
    private String status;

    public Patient(String name, String disease, String status) {
        this.name = name;
        this.disease = disease;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDisease() {
        return disease;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
