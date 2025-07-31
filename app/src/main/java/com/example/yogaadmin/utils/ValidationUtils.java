package com.example.yogaadmin.utils;

import android.util.Patterns;

public class ValidationUtils {

    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return isNotEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return isNotEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }
}