package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientActivity extends AppCompatActivity implements PatientsAdapter.OnPatientActionListener {
    private RecyclerView recyclerPatients;
    private PatientsAdapter adapter;
    private List<Patient> patientList = new ArrayList<>();
    private List<Patient> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient);

        recyclerPatients = findViewById(R.id.recycler_patients);
        recyclerPatients.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter BEFORE calling loadPatients
        adapter = new PatientsAdapter(filteredList, this);
        recyclerPatients.setAdapter(adapter);

        loadPatients();

        findViewById(R.id.btn_new).setOnClickListener(v -> filterPatients("New"));
        findViewById(R.id.btn_active).setOnClickListener(v -> filterPatients("Active"));
        findViewById(R.id.btn_completed).setOnClickListener(v -> filterPatients("Completed"));
    }

    private void loadPatients() {
        patientList.add(new Patient("John Doe", "Fever", "New"));
        patientList.add(new Patient("Jane Smith", "Flu", "Active"));
        patientList.add(new Patient("Alice Brown", "Diabetes", "Completed"));

        // Ensure adapter is updated only when it's initialized
        if (adapter != null) {
            filterPatients("New");
        }
    }

    private void filterPatients(String status) {
        filteredList.clear();
        filteredList.addAll(patientList.stream()
                .filter(p -> p.getStatus().equals(status))
                .collect(Collectors.toList()));

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAccept(Patient patient) {
        Toast.makeText(this, patient.getName() + " Accepted", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, UserDetailActivity.class);
        startActivity(i);
    }

    @Override
    public void onDecline(Patient patient) {
        Toast.makeText(this, patient.getName() + " Declined", Toast.LENGTH_SHORT).show();
        removePatient(patient);
    }

    private void removePatient(Patient patient) {
        patientList.remove(patient);
        filteredList.remove(patient);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, patient.getName() + " Removed", Toast.LENGTH_SHORT).show();
    }
}
