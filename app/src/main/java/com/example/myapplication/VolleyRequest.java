package com.example.myapplication;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class VolleyRequest {
    private static String URL = "https://f505-2401-4900-57b0-9229-b01e-f3b9-c27d-1cfa.ngrok-free.app/api/";

    private static Boolean result= true;

    public static boolean registerUser(Context con, String fullname, String user, String pass, String bloodGroup)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", fullname);
            jsonObject.put("email", user);
            jsonObject.put("password", pass);
            jsonObject.put("bloodGroup", bloodGroup);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Create Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL+"user/register", jsonObject,
                response -> {
                    result = true;
//                    Toast.makeText(con,"User Registred",Toast.LENGTH_SHORT).show();
                },
                error -> {
                    result=false;
                Log.d(TAG, "Error message: "+ error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json"); // Set content type
                return headers;
            }
        };

        // Add Request to Queue
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        requestQueue.add(jsonObjectRequest);
        return result;
    }

    public static boolean loginUser(Context context,String email,String pass)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Create Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL+"user/login", jsonObject,
                response -> {
                    result = true;
//                    Toast.makeText(context,"response : "+response.toString(),Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response: "+response.toString());

                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    try {
                        JSONObject data=new JSONObject(response.toString());
//                        Toast.makeText(context,"Name " + data.getString("name"),Toast.LENGTH_SHORT).show();
                        editor.putLong("id", data.getLong("id"));
                        editor.putString("name", data.getString("name"));
                        editor.putString("email", data.getString("email"));
                        editor.putString("bloodGroup", data.getString("bloodGroup"));
                        editor.putString("apiKey", data.getString("apiKey"));
                        editor.apply();

//                        Toast.makeText(context,"Name " + sharedPreferences.getString("name","No name"),Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        System.out.println("Exception : "+e.getMessage());
                        throw new RuntimeException(e);
                    }
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    // Mark user as logged in
                },
                error -> {
                    result=false;
                    Toast.makeText(context, "Error Message : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error message: "+ error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json"); // Set content type
                return headers;
            }
        };

        // Add Request to Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return result;
    }

    public static Vector<String> getDoctorCategory(Context context, LinearLayout specializationLayout)
    {
        Vector<String> v = new Vector<>();

        JSONObject jsonObject = new JSONObject();
        // Create Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL+"specialization", jsonObject,
                response -> {

//                    Toast.makeText(context,"Doctor Specialization : "+ response.toString(),Toast.LENGTH_SHORT);
                    Log.d(TAG, "getDoctorCategory: " + response.toString());
                    result = true;
                    try {
                        // Extract the specializations array
                        JSONArray specializationsArray = response.getJSONArray("specializations");

                        // Check if the array is empty
                        if (specializationsArray.length() == 0) {
                            Log.d(TAG, "No specializations found!");
                        } else {
                            // Loop through the array and get specialization names
                            specializationLayout.removeAllViews();
                            for (int i = 0; i < specializationsArray.length(); i++) {
                                JSONObject specializationObject = specializationsArray.getJSONObject(i);
                                String specializationName = specializationObject.getString("specialization");
                                Button b1 = createSpecializationButton(context,specializationName);
                                specializationLayout.addView(b1);
                                v.add(specializationName);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(context,"response : "+response.toString(),Toast.LENGTH_SHORT).show();
                },
                error -> {
//                    Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error: " + error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json"); // Set content type
                return headers;
            }

        };

        // Add Request to Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

        return v;
    }

    public static void getDailyFollowUp(Context context,String userId,LinearLayout specializationLayout,LinearLayout medicinelistlayout)
    {
        Vector<String> v = new Vector<>();

        JSONObject jsonObject1 = new JSONObject();

        // Create Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL+"emr/user/"+ userId, jsonObject1,
                response -> {

                    Log.d(TAG, "Follow Up: " + response.toString());
                    result = true;
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());

                            specializationLayout.removeAllViews();
                        int i = 1;
                            for (i = 1; i <= (jsonObject.getInt("noOfDays") - 1 - jsonObject.getInt("daysRemaining")); i++) {

                                AppCompatButton b1 = taskDone(context,String.valueOf(i + 1));
                                specializationLayout.addView(b1);
                        }
                        specializationLayout.addView(todayTask(context,String.valueOf(i)));
//
                        for (; i < jsonObject.getInt("noOfDays"); i++) {

                            AppCompatButton b1 = taskPendig(context,String.valueOf(i + 1));
                            specializationLayout.addView(b1);
                        }

                        //For Medicine List
                        JSONArray medicineList = jsonObject.getJSONArray("medicines");
                        Log.d(TAG, "medicine list: " + medicineList.toString());
//                        Toast.makeText(context,response.getJSONArray("medicines").toString(),Toast.LENGTH_SHORT);

                        // Check if the array is empty
                        if (medicineList.length() == 0) {
                            Log.d(TAG, "No Medicine found!");
                        } else {
                            // Loop through the array and get specialization names
                            medicinelistlayout.removeAllViews();
                            for (int j = 0; j < medicineList.length(); j++) {
                                String medinename = medicineList.getString(j);
                                Log.d(TAG, "medinename: " + medinename);

                                CheckBox c1=new CheckBox(context);
                                c1.setId(i);
                                c1.setText(medinename);
                                c1.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));

                                medicinelistlayout.addView(c1);
                            }
                            AppCompatButton b1 = taskDone(context,String.valueOf("Done"));
                            b1.setGravity(Gravity.CENTER);
                            medicinelistlayout.addView(b1);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(context,"Follow up : "+response.toString(),Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "getDailyFollowUp: " + response.toString());
                    },
                error -> {
                    Toast.makeText(context, "Follow up Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error: " + error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json"); // Set content type
                return headers;
            }

        };

        // Add Request to Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }



    public static Button createSpecializationButton(Context context, String specialization) {
        Button button = new Button(context);
        button.setText(specialization);
        button.setId(View.generateViewId());
        button.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Set margin
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
        params.setMargins(0, 0, 10, 0); // Left, Top, Right, Bottom
        button.setLayoutParams(params);

        // Set background tint color
//        button.setBackgroundColor(Color.parseColor("#C9F95F"));
        button.setBackgroundColor(Color.parseColor("#C9F95F"));

        // Set text color
        button.setTextColor(ContextCompat.getColor(context, android.R.color.black));

        // Set padding for better UI appearance
        button.setPadding(20, 10, 20, 10);

        return button;
    }

    public static AppCompatButton taskDone(Context context,String number)
    {
        AppCompatButton button = new AppCompatButton(context);
        button.setText("Day "+number);
        button.setTextColor(Color.BLACK);
        button.setBackgroundResource(R.drawable.taskdone);

        // Set Layout Parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 10, 0); // Set margin (left, top, right, bottom)
        button.setLayoutParams(layoutParams);
        return  button;
    }

    public static AppCompatButton taskPendig(Context context,String number)
    {
        AppCompatButton button = new AppCompatButton(context);
        button.setText("Day "+number);
        button.setTextColor(Color.BLACK);
        button.setBackgroundResource(R.drawable.taskpending);

        // Set Layout Parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 10, 0); // Set margin (left, top, right, bottom)
        button.setLayoutParams(layoutParams);
        return  button;
    }

    public static AppCompatButton todayTask(Context context,String number)
    {
        AppCompatButton button = new AppCompatButton(context);
        button.setText("Day "+number);
        button.setTextColor(Color.BLACK);
        button.setBackgroundResource(R.drawable.todaytask);

        // Set Layout Parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 10, 0); // Set margin (left, top, right, bottom)
        button.setLayoutParams(layoutParams);
        return  button;
    }


}
