package com.example.medicalcabinet;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppointmentActivity extends BaseActivity {

    RecyclerView recyclerView;
    AppointmentAdapter adapter;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler_appointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EditText etPatient = findViewById(R.id.et_patient_name);
        EditText etDate    = findViewById(R.id.et_date);
        EditText etTime    = findViewById(R.id.et_time);
        EditText etReason  = findViewById(R.id.et_reason);
        Button   btnAdd    = findViewById(R.id.btn_add_appointment);

        btnAdd.setOnClickListener(v -> {
            String patient = etPatient.getText().toString().trim();
            String date    = etDate.getText().toString().trim();
            String time    = etTime.getText().toString().trim();
            String reason  = etReason.getText().toString().trim();

            if (patient.isEmpty() || date.isEmpty()) {
                Toast.makeText(this,
                        getString(R.string.error_patient_date_required),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            db.addAppointment(patient, date, time, reason);
            Toast.makeText(this,
                    getString(R.string.success_add_appointment),
                    Toast.LENGTH_SHORT).show();
            etPatient.setText("");
            etDate.setText("");
            etTime.setText("");
            etReason.setText("");
            loadAppointments();
        });

        loadAppointments();
    }

    private void loadAppointments() {
        List<String[]> appts = db.getAllAppointments();
        adapter = new AppointmentAdapter(appts, db, this, () -> loadAppointments());
        recyclerView.setAdapter(adapter);
    }
}