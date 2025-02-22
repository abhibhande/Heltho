package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class UserProfile extends AppCompatActivity {

    private TextInputEditText editDob;
    private RadioGroup genderGroup;
    private MaterialButton btnUpdate, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI Components
        editDob = findViewById(R.id.edit_dob);
        genderGroup = findViewById(R.id.gender_group);
        btnUpdate = findViewById(R.id.btn_update_details);
        btnReset = findViewById(R.id.btn_reset);

        // Set OnClickListener for Date of Birth field
        editDob.setOnClickListener(v -> showDatePicker());

        // Set OnClickListener for Reset Button
        btnReset.setOnClickListener(v -> resetFields());

        // Set OnClickListener for Update Button
        btnUpdate.setOnClickListener(v -> updateProfile());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    editDob.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void resetFields() {
        editDob.setText("");
        genderGroup.clearCheck();
        Toast.makeText(this, "Fields Reset", Toast.LENGTH_SHORT).show();
    }

    private void updateProfile() {
        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        String gender = "";

        if (selectedGenderId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedGenderId);
            gender = selectedRadioButton.getText().toString();
        }

        String dob = editDob.getText().toString();

        if (dob.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Profile Updated!\nDOB: " + dob + "\nGender: " + gender, Toast.LENGTH_LONG).show();
        }
    }
}
