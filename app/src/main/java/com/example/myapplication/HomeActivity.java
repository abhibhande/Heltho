package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DoctorAdapter adapter;
    List<Doctor> doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Static doctor list
        doctorList = Arrays.asList(
                new Doctor("Dr. John Doe", "Cardiologist", "https://example.com/image1.jpg", "Experienced cardiologist.", Arrays.asList("10:00 AM", "2:00 PM")),
                new Doctor("Dr. Sarah Smith", "Dermatologist", "https://example.com/image2.jpg", "Expert in skin care.", Arrays.asList("9:00 AM", "4:00 PM"))
        );

        adapter = new DoctorAdapter(this, doctorList);
        recyclerView.setAdapter(adapter);
    }
}
