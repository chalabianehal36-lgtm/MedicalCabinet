package com.example.medicalcabinet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    List<String[]> data;
    DatabaseHelper db;
    Context ctx;

    public PatientAdapter(List<String[]> data, DatabaseHelper db, Context ctx) {
        this.data = data;
        this.db = db;
        this.ctx = ctx;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx)
                .inflate(R.layout.item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] p = data.get(position);
        // p[0]=id, p[1]=name, p[2]=age, p[3]=phone, p[4]=history

        holder.tvName.setText(p[1]);

        // العمر والهاتف مع الترجمة
        holder.tvDetails.setText(
                ctx.getString(R.string.age) + ": " + p[2] +
                        "   |   📞 " + p[3]
        );

        // التاريخ المرضي مع الترجمة
        holder.tvHistory.setText(
                p[4] != null && !p[4].isEmpty()
                        ? p[4]
                        : ctx.getString(R.string.no_medical_history)
        );

        // زر التعديل
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, AddPatientActivity.class);
            intent.putExtra("edit_mode",       true);
            intent.putExtra("patient_id",      p[0]);
            intent.putExtra("patient_name",    p[1]);
            intent.putExtra("patient_age",     p[2]);
            intent.putExtra("patient_phone",   p[3]);
            intent.putExtra("patient_history", p[4]);
            ctx.startActivity(intent);
        });

        // زر الحذف
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(ctx)
                    .setTitle(ctx.getString(R.string.delete))
                    .setMessage(ctx.getString(R.string.confirm_delete_patient))
                    .setPositiveButton(ctx.getString(R.string.delete), (d, w) -> {
                        db.deletePatient(Integer.parseInt(p[0]));
                        data.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, data.size());
                    })
                    .setNegativeButton(ctx.getString(R.string.cancel), null)
                    .show();
        });
    }

    // تحديث القائمة
    public void updateList(List<String[]> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    // فلترة البحث
    public void filter(String query) {
        // يتم التنفيذ من PatientListActivity عبر updateList
    }

    @Override public int getItemCount() { return data.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetails, tvHistory;
        ImageButton btnEdit, btnDelete;
        ViewHolder(View v) {
            super(v);
            tvName    = v.findViewById(R.id.tvPatientName);
            tvDetails = v.findViewById(R.id.tvPatientDetails);
            tvHistory = v.findViewById(R.id.tvPatientHistory);
            btnEdit   = v.findViewById(R.id.btnEditPatient);
            btnDelete = v.findViewById(R.id.btnDeletePatient);
        }
    }
}