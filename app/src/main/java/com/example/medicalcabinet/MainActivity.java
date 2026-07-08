package com.example.medicalcabinet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.card.MaterialCardView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends BaseActivity {

    DatabaseHelper db;
    SharedPreferences prefs;
    private static final int NOTIF_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db    = new DatabaseHelper(this);
        prefs = getSharedPreferences("MedicalCabinetPrefs", MODE_PRIVATE);

        requestNotificationPermission();

        // زر الخروج
        Button btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                prefs.edit().putBoolean("is_logged_in", false).apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });
        }


        ImageButton btnSettings = findViewById(R.id.btnSettings);
        if (btnSettings != null) {
            btnSettings.setOnClickListener(v ->
                    startActivity(new Intent(this, SettingsActivity.class)));
        }


        MaterialCardView cardPatients = findViewById(R.id.cardPatients);
        if (cardPatients != null) {
            cardPatients.setOnClickListener(v ->
                    startActivity(new Intent(this, PatientListActivity.class)));
        }


        MaterialCardView cardAppointments = findViewById(R.id.cardAppointments);
        if (cardAppointments != null) {
            cardAppointments.setOnClickListener(v ->
                    startActivity(new Intent(this, AppointmentActivity.class)));
        }


        MaterialCardView cardStats = findViewById(R.id.cardStats);
        if (cardStats != null) {
            cardStats.setOnClickListener(v ->
                    startActivity(new Intent(this, StatsActivity.class)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboard();
    }

    private void loadDashboard() {
        String today = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date());

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        if (tvGreeting != null) {
            String name = prefs.getString("full_name", "");
            tvGreeting.setText(getString(R.string.greeting) + " " + name);
        }

        TextView tvDate = findViewById(R.id.tvDate);
        if (tvDate != null) {
            tvDate.setText("📅  " + today);
        }

        TextView tvPatCount  = findViewById(R.id.tvPatientCount);
        TextView tvApptCount = findViewById(R.id.tvApptCount);
        TextView tvToday     = findViewById(R.id.tvTodayCount);

        if (tvPatCount != null)
            tvPatCount.setText(String.valueOf(db.getPatientsCount()));
        if (tvApptCount != null)
            tvApptCount.setText(String.valueOf(db.getAppointmentsCount()));
        if (tvToday != null)
            tvToday.setText(String.valueOf(db.getTodayAppointmentsCount(today)));

        NotificationHelper.sendTodayReminder(this, db, today);
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        NOTIF_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIF_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String today = new SimpleDateFormat("yyyy-MM-dd",
                        Locale.getDefault()).format(new Date());
                NotificationHelper.sendTodayReminder(this, db, today);
            }
        }
    }
}