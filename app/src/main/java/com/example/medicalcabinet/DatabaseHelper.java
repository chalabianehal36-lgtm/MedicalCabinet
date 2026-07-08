package com.example.medicalcabinet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "medical_cabinet.db";
    private static final int DB_VERSION = 2;

    // Table Patients
    public static final String TABLE_PATIENTS      = "patients";
    public static final String COL_PATIENT_ID      = "id";
    public static final String COL_NAME            = "name";
    public static final String COL_AGE             = "age";
    public static final String COL_PHONE           = "phone";
    public static final String COL_MEDICAL_HISTORY = "medical_history";

    // Table Appointments
    public static final String TABLE_APPOINTMENTS  = "appointments";
    public static final String COL_APPT_ID         = "id";
    public static final String COL_PATIENT_NAME    = "patient_name";
    public static final String COL_DATE            = "date";
    public static final String COL_TIME            = "time";
    public static final String COL_REASON          = "reason";
    public static final String COL_STATUS          = "status";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // جدول المرضى
        db.execSQL("CREATE TABLE " + TABLE_PATIENTS + " (" +
                COL_PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_AGE + " INTEGER, " +
                COL_PHONE + " TEXT, " +
                COL_MEDICAL_HISTORY + " TEXT)");

        // جدول المواعيد مع عمود الحالة
        db.execSQL("CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                COL_APPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PATIENT_NAME + " TEXT NOT NULL, " +
                COL_DATE + " TEXT, " +
                COL_TIME + " TEXT, " +
                COL_REASON + " TEXT, " +
                COL_STATUS + " TEXT DEFAULT 'waiting')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // إضافة عمود الحالة للجدول القديم بدون حذف البيانات
            try {
                db.execSQL("ALTER TABLE " + TABLE_APPOINTMENTS +
                        " ADD COLUMN " + COL_STATUS + " TEXT DEFAULT 'waiting'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ==================== PATIENTS ====================

    public long addPatient(String name, int age, String phone, String history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_AGE, age);
        values.put(COL_PHONE, phone);
        values.put(COL_MEDICAL_HISTORY, history);
        return db.insert(TABLE_PATIENTS, null, values);
    }

    public List<String[]> getAllPatients() {
        List<String[]> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PATIENTS, null, null, null,
                null, null, COL_NAME + " ASC");
        while (cursor.moveToNext()) {
            list.add(new String[]{
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PATIENT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_AGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_MEDICAL_HISTORY))
            });
        }
        cursor.close();
        return list;
    }

    public List<String[]> searchPatients(String query) {
        List<String[]> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PATIENTS, null,
                COL_NAME + " LIKE ?",
                new String[]{"%" + query + "%"},
                null, null, COL_NAME + " ASC");
        while (cursor.moveToNext()) {
            list.add(new String[]{
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PATIENT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_AGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_MEDICAL_HISTORY))
            });
        }
        cursor.close();
        return list;
    }

    public int updatePatient(int id, String name, int age,
                             String phone, String history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_AGE, age);
        values.put(COL_PHONE, phone);
        values.put(COL_MEDICAL_HISTORY, history);
        return db.update(TABLE_PATIENTS, values,
                COL_PATIENT_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deletePatient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PATIENTS, COL_PATIENT_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    // ==================== APPOINTMENTS ====================

    public long addAppointment(String patientName, String date,
                               String time, String reason) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PATIENT_NAME, patientName);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_REASON, reason);
        values.put(COL_STATUS, "waiting");
        return db.insert(TABLE_APPOINTMENTS, null, values);
    }

    public List<String[]> getAllAppointments() {
        List<String[]> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APPOINTMENTS, null, null, null,
                null, null, COL_DATE + " ASC");
        while (cursor.moveToNext()) {
            list.add(new String[]{
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_APPT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PATIENT_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_REASON)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS))
            });
        }
        cursor.close();
        return list;
    }

    public void updateAppointmentStatus(int id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, status);
        db.update(TABLE_APPOINTMENTS, values,
                COL_APPT_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteAppointment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPOINTMENTS, COL_APPT_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    // ==================== STATISTICS ====================

    public int getPatientsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_PATIENTS, null);
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        return count;
    }

    public int getAppointmentsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_APPOINTMENTS, null);
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        return count;
    }

    public int getTodayAppointmentsCount(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_APPOINTMENTS +
                        " WHERE " + COL_DATE + "=?", new String[]{today});
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        return count;
    }

    public int getCountByStatus(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_APPOINTMENTS +
                        " WHERE " + COL_STATUS + "=?", new String[]{status});
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        return count;
    }

    public List<String[]> getTodayAppointments(String today) {
        List<String[]> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APPOINTMENTS, null,
                COL_DATE + "=?", new String[]{today},
                null, null, COL_TIME + " ASC");
        while (cursor.moveToNext()) {
            list.add(new String[]{
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_APPT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PATIENT_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_REASON)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS))
            });
        }
        cursor.close();
        return list;
    }
}