package com.example.medicalcabinet;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddPatientActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        EditText etName    = findViewById(R.id.etName);
        EditText etAge     = findViewById(R.id.etAge);
        EditText etPhone   = findViewById(R.id.etPhone);
        EditText etHistory = findViewById(R.id.etHistory);
        Button   btnSave   = findViewById(R.id.btnSave);
        TextView tvTitle   = findViewById(R.id.tvFormTitle);

        DatabaseHelper db = new DatabaseHelper(this);

        boolean editMode  = getIntent().getBooleanExtra("edit_mode", false);
        int patientId     = -1;

        if (editMode) {
            patientId = Integer.parseInt(getIntent().getStringExtra("patient_id"));
            tvTitle.setText(getString(R.string.edit_patient));
            btnSave.setText(getString(R.string.save_edits));
            etName.setText(getIntent().getStringExtra("patient_name"));
            etAge.setText(getIntent().getStringExtra("patient_age"));
            etPhone.setText(getIntent().getStringExtra("patient_phone"));
            etHistory.setText(getIntent().getStringExtra("patient_history"));
        } else {
            tvTitle.setText(getString(R.string.add_new_patient));
            btnSave.setText(getString(R.string.save));
        }

        final int finalPatientId = patientId;

        btnSave.setOnClickListener(v -> {
            String name    = etName.getText().toString().trim();
            String ageStr  = etAge.getText().toString().trim();
            String phone   = etPhone.getText().toString().trim();
            String history = etHistory.getText().toString().trim();

            if (name.isEmpty() || ageStr.isEmpty()) {
                Toast.makeText(this,
                        getString(R.string.error_name_age_required),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageStr);

            if (editMode) {
                int rows = db.updatePatient(finalPatientId, name, age, phone, history);
                Toast.makeText(this,
                        rows > 0
                                ? getString(R.string.success_edit)
                                : getString(R.string.error_edit),
                        Toast.LENGTH_SHORT).show();
            } else {
                long result = db.addPatient(name, age, phone, history);
                Toast.makeText(this,
                        result != -1
                                ? getString(R.string.success_add_patient)
                                : getString(R.string.error_save),
                        Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}