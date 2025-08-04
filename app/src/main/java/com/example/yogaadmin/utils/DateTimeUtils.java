package com.example.yogaadmin.utils;

import java.util.Locale;

/**
 * DateTimeUtils provides utility methods for date and time operations.
 * This class contains helper methods for converting between different date/time
 * formats and handling locale-specific date operations.
 * 
 * The utility methods support:
 * - Converting day names to numeric day of week values
 * - Handling both full and short day name formats
 * - Locale-aware date operations
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class DateTimeUtils {

    /**
     * Converts a day name string to its corresponding numeric day of week value.
     * This method supports both full day names (e.g., "Monday") and short day names (e.g., "Mon")
     * and is locale-aware to handle different language formats.
     * 
     * The method first tries to match against full weekday names, then falls back to
     * short weekday names if no match is found. Returns -1 if no match is found.
     * 
     * @param dayName The day name string to convert (e.g., "Monday", "Mon")
     * @param locale The locale to use for day name matching
     * @return The numeric day of week (1-7, where 1 is Sunday), or -1 if no match found
     */
    public static int getDayOfWeekFromString(String dayName, Locale locale) {
        // Try to match against full weekday names first
        String[] weekdays = new java.text.DateFormatSymbols(locale).getWeekdays();
        for (int i = 0; i < weekdays.length; i++) {
            if (weekdays[i].equalsIgnoreCase(dayName)) {
                return i;
            }
        }
        
        // Fallback to short day names if no match found
        String[] shortWeekdays = new java.text.DateFormatSymbols(locale).getShortWeekdays();
        for (int i = 0; i < shortWeekdays.length; i++) {
            if (shortWeekdays[i].equalsIgnoreCase(dayName)) {
                return i;
            }
        }
        
        // Return -1 if no match is found
        return -1;
    }
}