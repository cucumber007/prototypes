package com.cucumber007.prototypes.activities.giver.objects;


import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("full_name") String name;
    @SerializedName("birth_date") String birthDate;
    String email;
    @SerializedName("phone_number") String phoneNumber;
    Integer gender;
    String country;
    String city;

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setGender(Boolean isMale) {
        if(isMale == null) setGender(2);
        else setGender(isMale ? 0 : 1);
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
