package com.example.medicalcabinet;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StatsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        DatabaseHelper db = new DatabaseHelper(this);
        String today = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date());

        TextView tvTotalPatients  = findViewById(R.id.tvTotalPatients);
        TextView tvTotalAppts     = findViewById(R.id.tvTotalAppts);
        TextView tvPresent        = findViewById(R.id.tvPresent);
        TextView tvAbsent         = findViewById(R.id.tvAbsent);
        TextView tvWaiting        = findViewById(R.id.tvWaiting);
        TextView tvToday          = findViewById(R.id.tvTodayAppts);

        tvTotalPatients.setText(String.valueOf(db.getPatientsCount()));
        tvTotalAppts.setText(String.valueOf(db.getAppointmentsCount()));
        tvPresent.setText(String.valueOf(db.getCountByStatus("present")));
        tvAbsent.setText(String.valueOf(db.getCountByStatus("absent")));
        tvWaiting.setText(String.valueOf(db.getCountByStatus("waiting")));
        tvToday.setText(String.valueOf(db.getTodayAppointmentsCount(today)));
    }
}