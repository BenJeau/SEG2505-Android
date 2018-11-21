package com.seg2505.project;
import com.seg2505.project.activities.LoginActivity;
import com.seg2505.project.activities.ProviderInfoActivity;
import com.seg2505.project.model.Provider;

import static org.junit.Assert.*;
import org.junit.Test;

public class ProviderUnitTest {
    @Test
    public void checkRightStreetNumber() {
        Boolean valid = ProviderInfoActivity.isValidStreetNum("1234");
        assertEquals("Check if the street number is valid.", true, valid);
    }

    @Test
    public void checkWrongStreetNumber() {
        Boolean valid = ProviderInfoActivity.isValidStreetNum("1e5 L-4");
        assertEquals("Check if the street number is invalid.", false, valid);
    }

    @Test
    public void checkRightStreetName() {
        Boolean valid = ProviderInfoActivity.isValidStreetName("Vergenie Howayek-hello1");
        assertEquals("Check if street name is valid.", true, valid);
    }

    @Test
    public void checkWrongStreetName() {
        Boolean valid = ProviderInfoActivity.isValidStreetName("/15V*1a");
        assertEquals("Check if the street name is invalid.", false, valid);
    }

    @Test
    public void checkRightPostalCode() {
        Boolean valid = ProviderInfoActivity.isValidPostalCode("K1H6S5");
        assertEquals("Check if postal code is valid.", true, valid);
    }

    @Test
    public void checkWrongPostalCode() {
        Boolean valid = ProviderInfoActivity.isValidPostalCode("K1h 6S5");
        assertEquals("Check if postal code is invalid.", false, valid);
    }

    @Test
    public void checkRightCompanyName() {
        Boolean valid = ProviderInfoActivity.isValidCompanyName("Vergenie.co-hjm hnh");
        assertEquals("Check if company name is valid.", true, valid);
    }

    @Test
    public void checkWrongCompanyName() {
        Boolean valid = ProviderInfoActivity.isValidCompanyName("Vergenie1*");
        assertEquals("Check if company name is invalid.", false, valid);
    }

    @Test
    public void checkRightDescription() {
        Boolean valid =  ProviderInfoActivity.isValidDescription("Hi, my name is Vergenie.");
        assertEquals("Check if description is valid.", true, valid);
    }

    @Test
    public void checkWrongDescription() {
        Boolean valid = ProviderInfoActivity.isValidDescription("Hi, my name is Vergenie and these are my offered services. Don't worry, you will not be disappointed as our services will never let you down.Hi, my name is Vergenie and these are my offered services. Don't worry, you will not be disappointed as our services will never let you down.Hi, my name is Vergenie and these are my offered services. Don't worry, you will not be disappointed as our services will never let you down.");
        assertEquals("Check if description is invalid.", false, valid);
    }

    @Test
    public void checkRightPhoneNumber() {
        Boolean valid = ProviderInfoActivity.isValidPhoneNumber("6131234567");
        assertEquals("Check if phone number is valid.", true, valid);
    }

    @Test
    public void checkWrongPhoneNumber() {
        Boolean valid = ProviderInfoActivity.isValidPhoneNumber("613 123 4567");
        assertEquals("Check if phone number is invalid.", false, valid);
    }

    @Test
    public void checkEmpty() {
        Boolean valid = ProviderInfoActivity.empty("      ");
        assertEquals("Check if string is empty.", false, valid);
    }

    @Test
    public void checkNotEmpty() {
        Boolean valid = ProviderInfoActivity.empty("hello");
        assertEquals("Check if string is not empty", true, valid);
    }
}
