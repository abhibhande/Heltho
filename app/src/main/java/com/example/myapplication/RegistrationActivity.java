package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, passwordLayout, dropdownLayout;
    private TextInputEditText user, pass;
    private MaterialAutoCompleteTextView dropdown_menu;
    private MaterialButton Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        // Initialize views
        emailLayout = findViewById(R.id.email);
        passwordLayout = findViewById(R.id.password);
        dropdownLayout = findViewById(R.id.dropdown);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        dropdown_menu = findViewById(R.id.dropdown_menu);
        Signup = findViewById(R.id.Signup);

        Signup.setEnabled(false);

        // Blood group dropdown menu
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, bloodGroups);
        dropdown_menu.setAdapter(adapter);

        // Input validation listeners
        user.addTextChangedListener(inputWatcher);
        pass.addTextChangedListener(inputWatcher);
        dropdown_menu.addTextChangedListener(inputWatcher);

        // Signup button click listener
        Signup.setOnClickListener(v -> {
            if (validateInputs()) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private final TextWatcher inputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validateInputs();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    // Function to validate inputs
    private boolean validateInputs() {
        String email = user.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String bloodGroup = dropdown_menu.getText().toString().trim();

        boolean isValid = true;

        if (email.isEmpty()) {
            emailLayout.setError("Email is required");
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
            isValid = false;
        } else {
            passwordLayout.setError(null);
        }

        if (bloodGroup.isEmpty()) {
            dropdownLayout.setError("Choose a Blood Group");
            isValid = false;
        } else {
            dropdownLayout.setError(null);
        }

        Signup.setEnabled(isValid);
        return isValid;
    }
}
