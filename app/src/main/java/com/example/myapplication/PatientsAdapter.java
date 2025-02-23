package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {

    private final List<Patient> patients;
    private final OnPatientActionListener listener;

    public interface OnPatientActionListener {
        void onAccept(Patient patient);
        void onDecline(Patient patient);
    }

    public PatientsAdapter(List<Patient> patients, OnPatientActionListener listener) {
        this.patients = patients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = patients.get(position);
        holder.txtName.setText(patient.getName());
        holder.txtDisease.setText(patient.getDisease());

        holder.btnAccept.setOnClickListener(v -> listener.onAccept(patient));
        holder.btnDecline.setOnClickListener(v -> listener.onDecline(patient));
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDisease;
        MaterialButton btnAccept, btnDecline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDisease = itemView.findViewById(R.id.txt_disease);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnDecline = itemView.findViewById(R.id.btn_decline);
        }
    }
}
