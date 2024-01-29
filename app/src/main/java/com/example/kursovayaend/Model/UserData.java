package com.example.kursovayaend.Model;

public class UserData {

    private float weight;
    private float height;
    private String gender;

    public UserData(float weight, float height, String gender) {
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }
}
