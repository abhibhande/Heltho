package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText user, pass;
    private MaterialButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize Views
        emailLayout = findViewById(R.id.email);
        passwordLayout = findViewById(R.id.password);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        loginButton = findViewById(R.id.button);

        // Add TextWatcher to enable/disable button dynamically
        user.addTextChangedListener(inputWatcher);
        pass.addTextChangedListener(inputWatcher);

        // Set click listener for login button
        loginButton.setOnClickListener(v -> verify());

        // Adjust window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initially disable the button
        loginButton.setEnabled(false);
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

    private void validateInputs() {
        String email = user.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (email.isEmpty()) {
            emailLayout.setError("Email is required");
        } else {
            emailLayout.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
        } else {
            passwordLayout.setError(null);
        }

        loginButton.setEnabled(!email.isEmpty() && password.length() >= 6);
    }

    // Function to handle login verification
    public void verify() {
        String email = user.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (email.isEmpty()) {
            emailLayout.setError("Email is required");
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
            return;
        }

        // Here you can add authentication logic (e.g., check credentials from database)
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

        // Navigate to the next activity after successful login
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
