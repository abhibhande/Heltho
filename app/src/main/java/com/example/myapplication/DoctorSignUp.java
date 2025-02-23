package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

public class DoctorSignUp extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // Added constant for request code
    private Uri imageUri;

    private TextInputEditText fullName, email, password, phone;
    private MaterialAutoCompleteTextView specializationDropdown;
    private MaterialButton btnUploadImage, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_sign_up);

        // Initialize UI elements
        btnUploadImage = findViewById(R.id.btn_upload_image);
        fullName = findViewById(R.id.fname);
        email = findViewById(R.id.user);
        password = findViewById(R.id.pass);
        specializationDropdown = findViewById(R.id.dropdown_menu);
        phone = findViewById(R.id.phoneNo);
        btnSignUp = findViewById(R.id.Signup);

        btnUploadImage.setOnClickListener(view -> openFileChooser());

        // Handle SignUp Button Click
        btnSignUp.setOnClickListener(view -> registerUser());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Toast.makeText(this, "Image Selected Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {
        String name = fullName.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String specialization = specializationDropdown.getText().toString();
        String phoneNumber = phone.getText().toString();

        if (name.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || specialization.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
    }
}
