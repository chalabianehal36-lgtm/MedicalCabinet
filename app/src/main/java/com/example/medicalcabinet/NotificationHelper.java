package com.example.medicalcabinet;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import java.util.List;

public class NotificationHelper {

    private static final String CHANNEL_ID = "appointments_channel";

    public static void sendTodayReminder(Context ctx, DatabaseHelper db, String today) {
        List<String[]> appts = db.getTodayAppointments(today);
        if (appts.isEmpty()) return;

        NotificationManager manager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "مواعيد اليوم",
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        StringBuilder msg = new StringBuilder();
        for (String[] a : appts) {
            msg.append("• ").append(a[1]).append(" — ").append(a[3]).append("\n");
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("مواعيد اليوم (" + appts.size() + ")")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg.toString()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        manager.notify(1001, builder.build());
    }
}