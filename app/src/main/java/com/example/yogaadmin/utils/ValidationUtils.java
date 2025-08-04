package com.example.yogaadmin.utils;

import java.util.regex.Pattern;

/**
 * ValidationUtils provides utility methods for input validation throughout the application.
 * This class contains validation methods for common data types including email addresses,
 * phone numbers, and general string validation.
 * 
 * The validation methods support:
 * - Email address format validation
 * - Phone number format validation (10-digit format)
 * - Empty/null string checking
 * - Pattern-based validation using regular expressions
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class ValidationUtils {

    /**
     * Regular expression pattern for validating email addresses.
     * This pattern ensures email addresses follow standard format requirements:
     * - Local part: 1-256 characters, alphanumeric plus common symbols
     * - @ symbol separator
     * - Domain: alphanumeric with hyphens, 1-64 characters
     * - TLD: alphanumeric with hyphens, 1-25 characters
     * - Multiple subdomains supported
     */
    private static final Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"
            + "\\@"
            + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}"
            + "("
            + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}"
            + ")+"
    );

    /**
     * Regular expression pattern for validating phone numbers.
     * This pattern requires exactly 10 digits for standard phone number format.
     * Assumes US/Canada phone number format without country code.
     */
    private static final Pattern PHONE = Pattern.compile(
            "\\d{10}"
    );

    /**
     * Checks if a string is not null and not empty after trimming whitespace.
     * This method is commonly used as a prerequisite for other validation methods.
     * 
     * @param text The string to check for emptiness
     * @return true if the string is not null and contains non-whitespace characters, false otherwise
     */
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }  

    /**
     * Validates an email address format using regex pattern matching.
     * This method first checks if the email is not empty, then validates
     * the format against the EMAIL_ADDRESS pattern.
     * 
     * @param email The email address string to validate
     * @return true if the email is not empty and matches the email format pattern, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return isNotEmpty(email) && EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Validates a phone number format using regex pattern matching.
     * This method first checks if the phone number is not empty, then validates
     * the format against the PHONE pattern (exactly 10 digits).
     * 
     * @param phone The phone number string to validate
     * @return true if the phone number is not empty and matches the phone format pattern, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        return isNotEmpty(phone) && PHONE.matcher(phone).matches();
    }
}