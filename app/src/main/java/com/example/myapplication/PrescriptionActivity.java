package com.example.myapplication;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

public class PrescriptionActivity extends AppCompatActivity {
    private TextInputEditText medicationNameEdit;
    private RadioGroup timingRadioGroup;
    private ChipGroup mealTimingGroup;
    private MaterialButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        initializeViews();
        setupUserDetails();
        setupTiming();
        setupMealTiming();
        setupSaveButton();
    }

    private void initializeViews() {
        medicationNameEdit = findViewById(R.id.medicationNameEdit);
        timingRadioGroup = findViewById(R.id.timingRadioGroup);
        mealTimingGroup = findViewById(R.id.mealTimingGroup);
        saveButton = findViewById(R.id.saveButton);

        medicationNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInput();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupUserDetails() {
        String userName = "John Doe";  // Replace with actual data
        String bloodGroup = "O+";      // Replace with actual data

        TextView nameText = findViewById(R.id.nameTextView);
        TextView bloodGroupText = findViewById(R.id.bloodGroupTextView);

        nameText.setText(userName);
        bloodGroupText.setText("Blood Group: " + bloodGroup);
    }

    private void setupTiming() {
        timingRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            validateInput();
        });
    }

    private void setupMealTiming() {
        mealTimingGroup.setOnCheckedChangeListener((group, checkedId) -> {
            validateInput();
        });
    }

    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> saveMedicationDetails());
    }

    private void validateInput() {
        boolean isValid = !TextUtils.isEmpty(medicationNameEdit.getText())
                && timingRadioGroup.getCheckedRadioButtonId() != -1
                && mealTimingGroup.getCheckedChipId() != View.NO_ID;

        saveButton.setEnabled(isValid);
    }

    private void saveMedicationDetails() {
        String medicationName = medicationNameEdit.getText().toString();

        // Get selected timing
        int selectedTimingId = timingRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedTiming = findViewById(selectedTimingId);
        String timing = selectedTiming != null ? selectedTiming.getText().toString() : "";

        // Get meal timing
        int selectedMealId = mealTimingGroup.getCheckedChipId();
        Chip selectedMeal = findViewById(selectedMealId);
        String mealTiming = selectedMeal != null ? selectedMeal.getText().toString() : "";

        // Here you would save the data to your database/storage
        Toast.makeText(this,
                "Saved: " + medicationName + " - " + timing + " - " + mealTiming,
                Toast.LENGTH_SHORT).show();

        // Reset form
        medicationNameEdit.setText("");
        timingRadioGroup.clearCheck();
        mealTimingGroup.clearCheck();
    }
}