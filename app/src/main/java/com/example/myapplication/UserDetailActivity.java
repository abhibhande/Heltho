package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class UserDetailActivity extends AppCompatActivity {

    private TextView txtUserName, txtUserEmail, txtBloodGroup, txtDisease, txtDiseaseDesc;
    private MaterialButton btnPrescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_detail);

        txtUserName = findViewById(R.id.txt_user_name);
        txtUserEmail = findViewById(R.id.txt_user_email);
        txtBloodGroup = findViewById(R.id.txt_blood_group);
        txtDisease = findViewById(R.id.txt_disease);
        txtDiseaseDesc = findViewById(R.id.txt_disease_desc);
        btnPrescription = findViewById(R.id.btn_prescription);

        Intent intent = getIntent();
        if (intent != null) {
            txtUserName.setText(intent.getStringExtra("name"));
            txtUserEmail.setText(intent.getStringExtra("email"));
            txtBloodGroup.setText("Blood Group: " + intent.getStringExtra("bloodGroup"));
            txtDisease.setText("Disease: " + intent.getStringExtra("disease"));
            txtDiseaseDesc.setText("Description: " + intent.getStringExtra("description"));
        }

        btnPrescription.setOnClickListener(v -> {
            Intent prescriptionIntent = new Intent(UserDetailActivity.this, PrescriptionActivity.class);
            prescriptionIntent.putExtra("name", txtUserName.getText().toString());
            prescriptionIntent.putExtra("email", txtUserEmail.getText().toString());
            prescriptionIntent.putExtra("bloodGroup", txtBloodGroup.getText().toString());
            startActivity(prescriptionIntent);
        });
    }
}