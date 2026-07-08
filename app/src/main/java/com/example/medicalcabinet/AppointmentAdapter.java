package com.example.medicalcabinet;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    List<String[]> data;
    DatabaseHelper db;
    Context ctx;
    Runnable onDataChanged;

    public AppointmentAdapter(List<String[]> data, DatabaseHelper db,
                              Context ctx, Runnable onDataChanged) {
        this.data = data;
        this.db = db;
        this.ctx = ctx;
        this.onDataChanged = onDataChanged;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx)
                .inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] a = data.get(position);
        // a[0]=id, a[1]=name, a[2]=date, a[3]=time, a[4]=reason, a[5]=status

        holder.tvPatient.setText(a[1]);
        holder.tvDateTime.setText(a[2] + "  |  " + a[3]);
        holder.tvReason.setText(a[4]);

        // حالة الموعد مع الترجمة
        String status = a.length > 5 ? a[5] : "waiting";
        switch (status) {
            case "present":
                holder.tvStatus.setText(ctx.getString(R.string.status_present));
                holder.tvStatus.setBackgroundResource(R.drawable.rounded_tag_green);
                holder.tvStatus.setTextColor(0xFF2E7D32);
                break;
            case "absent":
                holder.tvStatus.setText(ctx.getString(R.string.status_absent));
                holder.tvStatus.setBackgroundResource(R.drawable.rounded_tag_red);
                holder.tvStatus.setTextColor(0xFFE53935);
                break;
            default:
                holder.tvStatus.setText(ctx.getString(R.string.status_waiting));
                holder.tvStatus.setBackgroundColor(0xFFFFF9C4);
                holder.tvStatus.setTextColor(0xFFF57F17);
                break;
        }

        // تغيير الحالة بالضغط
        holder.tvStatus.setOnClickListener(v -> {
            String[] options = {
                    ctx.getString(R.string.status_waiting),
                    ctx.getString(R.string.status_present),
                    ctx.getString(R.string.status_absent)
            };
            String[] values = {"waiting", "present", "absent"};

            new AlertDialog.Builder(ctx)
                    .setTitle(ctx.getString(R.string.change_status))
                    .setItems(options, (d, which) -> {
                        db.updateAppointmentStatus(
                                Integer.parseInt(a[0]), values[which]);
                        if (onDataChanged != null) onDataChanged.run();
                    })
                    .show();
        });

        // حذف الموعد
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(ctx)
                    .setTitle(ctx.getString(R.string.delete))
                    .setMessage(ctx.getString(R.string.confirm_delete_appt))
                    .setPositiveButton(ctx.getString(R.string.delete), (d, w) -> {
                        db.deleteAppointment(Integer.parseInt(a[0]));
                        data.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, data.size());
                    })
                    .setNegativeButton(ctx.getString(R.string.cancel), null)
                    .show();
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatient, tvDateTime, tvReason, tvStatus;
        ImageButton btnDelete;
        ViewHolder(View v) {
            super(v);
            tvPatient  = v.findViewById(R.id.tvApptPatient);
            tvDateTime = v.findViewById(R.id.tvApptDateTime);
            tvReason   = v.findViewById(R.id.tvApptReason);
            tvStatus   = v.findViewById(R.id.tvApptStatus);
            btnDelete  = v.findViewById(R.id.btnDeleteAppt);
        }
    }
}