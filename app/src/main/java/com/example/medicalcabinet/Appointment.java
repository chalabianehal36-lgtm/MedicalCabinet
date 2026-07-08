package com.example.medicalcabinet;

public class Appointment {
    private int id;
    private int patientId;
    private String patientName;
    private String date;
    private String time;
    private String reason;
    private String status; // "pending", "done", "cancelled"

    public Appointment() {}

    public Appointment(int patientId, String patientName, String date, String time, String reason) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.status = "pending";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
