package com.example.yogaadmin;

import com.example.yogaadmin.utils.ValidationUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidationUtilsTest {

    @Test
    public void email_isCorrect() {
        assertTrue(ValidationUtils.isValidEmail("test@example.com"));
    }

    @Test
    public void email_isIncorrect() {
        assertFalse(ValidationUtils.isValidEmail("test"));
    }

    @Test
    public void phone_isCorrect() {
        assertTrue(ValidationUtils.isValidPhone("1234567890"));
    }

    @Test
    public void phone_isIncorrect() {
        assertFalse(ValidationUtils.isValidPhone("123"));
    }
}


