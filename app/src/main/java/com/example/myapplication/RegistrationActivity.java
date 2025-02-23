package com.example.myapplication;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.service.chooser.ChooserResult;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.example.myapplication.VolleyRequest;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, passwordLayout, dropdownLayout;
    private TextInputEditText fullname,user, pass;
    private MaterialAutoCompleteTextView dropdown_menu;
    private MaterialButton Signup;

    private String URL = "https://aa3c-117-198-136-1.ngrok-free.app/api/user/register";
    private String ChooserResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        // Initialize views
//        TextInputEditText fullname = findViewById(R.id.fname);
        emailLayout = findViewById(R.id.email);
        passwordLayout = findViewById(R.id.password);
        dropdownLayout = findViewById(R.id.dropdown);

        fullname = findViewById(R.id.fname);
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
            if (validateInputs())
            {
                if(VolleyRequest.registerUser(this,fullname.getText().toString(),user.getText().toString(),pass.getText().toString(),dropdown_menu.getText().toString())) {
                    Toast.makeText(this,"User Registerd", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(this, "Error while registering", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private Boolean registerUser()
//    {
//        VolleyRequest.registerUser(this,fullname.getText().toString(),user.getText().toString(),pass.getText().toString(),dropdown_menu.getText().toString());
////        JSONObject jsonObject = new JSONObject();
////        try {
////            jsonObject.put("name", fullname.getText().toString());
////            jsonObject.put("email", user.getText().toString());
////            jsonObject.put("password", pass.getText().toString());
////            jsonObject.put("bloodGroup", dropdown_menu.getText().toString());
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////        // Create Request
////        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
////                response -> {
////                    Toast.makeText(RegistrationActivity.this, "Response: " + response.toString(), Toast.LENGTH_LONG).show();
////                },
////                error -> {
////                    Toast.makeText(RegistrationActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
////                    Log.d(TAG, "Error message: "+ error.getMessage());
////                }) {
////            @Override
////            public Map<String, String> getHeaders() throws AuthFailureError {
////                Map<String, String> headers = new HashMap<>();
////                headers.put("Content-Type", "application/json"); // Set content type
////                return headers;
////            }
////        };
////
////        // Add Request to Queue
////        RequestQueue requestQueue = Volley.newRequestQueue(this);
////        requestQueue.add(jsonObjectRequest);
//        return true;
//    }


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
