package com.example.myapplication;

import java.io.Serializable;
import java.util.List;

public class Doctor implements Serializable {
    private String name, specialization, imageUrl, bio;
    private List<String> availableSlots;

    public Doctor(String name, String specialization, String imageUrl, String bio, List<String> availableSlots) {
        this.name = name;
        this.specialization = specialization;
        this.imageUrl = imageUrl;
        this.bio = bio;
        this.availableSlots = availableSlots;
    }

    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getImageUrl() { return imageUrl; }
    public String getBio() { return bio; }
    public List<String> getAvailableSlots() { return availableSlots; }
}
