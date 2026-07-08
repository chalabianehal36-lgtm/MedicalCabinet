package com.example.medicalcabinet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class PatientListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PatientAdapter adapter;
    DatabaseHelper db;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerPatients);
        etSearch     = findViewById(R.id.etSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAddPatient);
        fab.setOnClickListener(v ->
                startActivity(new Intent(this, AddPatientActivity.class)));

        // البحث الفوري
        etSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void onTextChanged(CharSequence s, int st, int b, int c) {
                String q = s.toString().trim();
                List<String[]> results = q.isEmpty()
                        ? db.getAllPatients()
                        : db.searchPatients(q);
                adapter = new PatientAdapter(results, db, PatientListActivity.this);
                recyclerView.setAdapter(adapter);
            }
            public void afterTextChanged(Editable s) {}
        });

        loadPatients();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPatients();
    }

    private void loadPatients() {
        List<String[]> patients = db.getAllPatients();
        adapter = new PatientAdapter(patients, db, this);
        recyclerView.setAdapter(adapter);
    }
}