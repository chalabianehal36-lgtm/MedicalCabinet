package com.example.medicalcabinet;

public class Patient {
    private int id;
    private String name;
    private int age;
    private String phone;
    private String address;
    private String notes;
    private String createdAt;

    public Patient() {}

    public Patient(String name, int age, String phone, String address, String notes, String createdAt) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
