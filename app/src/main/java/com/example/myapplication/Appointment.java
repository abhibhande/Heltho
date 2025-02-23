package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Appointment extends AppCompatActivity {

    private MaterialButton btnPickDate, btnConfirmAppointment;
    private GridLayout gridTimeSlots;
    private String selectedDate = "", selectedTime = "";

    private final String[] timeSlots = {
            "8:00AM", "8:30AM", "9:00AM",
            "10:00AM", "10:30AM", "11:00AM",
            "11:30AM", "12:00AM", "12:30AM",
            "1:00AM", "1:30AM"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnPickDate = findViewById(R.id.btn_pick_date);
        btnConfirmAppointment = findViewById(R.id.btn_confirm_appointment);
        gridTimeSlots = findViewById(R.id.grid_time_slots);

        btnPickDate.setOnClickListener(v -> showDatePicker());
        createTimeSlotButtons();

        btnConfirmAppointment.setOnClickListener(v -> {
            if (!selectedDate.isEmpty() && !selectedTime.isEmpty()) {
                Toast.makeText(Appointment.this,
                        "Appointment set for " + selectedDate + " at " + selectedTime,
                        Toast.LENGTH_SHORT).show();

                // Redirecting to PatientsActivity
                Intent intent = new Intent(this, PatientActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            selectedDate = sdf.format(new Date(selection));
            btnPickDate.setText(selectedDate);
            checkButtonState();
        });
    }

    private void createTimeSlotButtons() {
        for (String time : timeSlots) {
            MaterialButton button = new MaterialButton(this);
            button.setText(time);
            button.setTextSize(14);
            button.setCornerRadius(16);
            button.setBackgroundColor(getResources().getColor(R.color.light_gray));
            button.setTextColor(getResources().getColor(android.R.color.black));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(8, 8, 8, 8);
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                selectedTime = time;
                updateTimeSlotSelection(button);
                checkButtonState();
            });

            gridTimeSlots.addView(button);
        }
    }

    private void updateTimeSlotSelection(MaterialButton selectedButton) {
        for (int i = 0; i < gridTimeSlots.getChildCount(); i++) {
            View child = gridTimeSlots.getChildAt(i);
            if (child instanceof MaterialButton) {
                ((MaterialButton) child).setBackgroundColor(getResources().getColor(R.color.light_gray));
                ((MaterialButton) child).setTextColor(getResources().getColor(android.R.color.black));
            }
        }
        selectedButton.setBackgroundColor(getResources().getColor(R.color.dark_gray));
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));
    }

    private void checkButtonState() {
        btnConfirmAppointment.setEnabled(!selectedDate.isEmpty() && !selectedTime.isEmpty());
        btnConfirmAppointment.setBackgroundColor(
                getResources().getColor(btnConfirmAppointment.isEnabled() ? R.color.dark_gray : R.color.gray)
        );
    }
}
