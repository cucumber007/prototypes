package com.cucumber007.prototypes.reusables.objects;


import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

public class UserData {

    @SerializedName("full_name") String name;
    @SerializedName("birth_date") String birthDate;
    String email;
    @SerializedName("phone_number") String phoneNumber;
    Integer gender;
    String country;
    String city;

    public boolean isFilled() {
        try {
            for(Field field : UserData.class.getDeclaredFields()) {
                Object value = field.get(this);
                if (value == null && !field.isSynthetic()) return false;
                if (value instanceof String && ((String) value).length() == 0) return false;
            }
        } catch (IllegalAccessException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
