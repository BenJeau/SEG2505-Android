package com.seg2505.project;
import static org.junit.Assert.*;
import org.junit.Test;

public class ServiceUnitTest {
    @Test
    public void checkWrongService() {
        Boolean valid = AdminServiceActivity.isFieldEmpty("");
        assertEquals("Check if the service is invalid.", true, valid);
    }

    @Test
    public void checkRightService() {
        Boolean valid = AdminServiceActivity.isFieldEmpty("Cleaning");
        assertEquals("Check if the service is valid.", false, valid);
    }

    @Test
    public void checkWrongHourlyRate() {
        Boolean valid = AdminServiceActivity.isDouble("");
        assertEquals("Check if the hourly rate is invalid.", false, valid);
    }

    @Test
    public void checkRightHourlyRate() {
        Boolean valid = AdminServiceActivity.isDouble("99.90");
        assertEquals("Check if hourly rate is valid.", true, valid);
    }
}
