package com.seg2505.project;
import com.seg2505.project.activities.LoginActivity;
import com.seg2505.project.activities.ProviderInfoActivity;

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
}
