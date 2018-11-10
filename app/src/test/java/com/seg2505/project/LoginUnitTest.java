package com.seg2505.project;
import com.seg2505.project.activities.LoginActivity;

import static org.junit.Assert.*;
import org.junit.Test;

public class LoginUnitTest {
    @Test
    public void checkWrongUserPassword() {
        Boolean valid = LoginActivity.isValidPassword("hi");
        assertEquals("Check if the password is invalid.", false, valid);
    }

    @Test
    public void checkRightUserPassword() {
        Boolean valid = LoginActivity.isValidPassword("BooMessi100%");
        assertEquals("Check if the password is valid.", true, valid);
    }

    @Test
    public void checkEmptyUserPassword() {
        Boolean valid = LoginActivity.isValidPassword("");
        assertEquals("Check if the password is invalid.", false, valid);

    }

    @Test
    public void checkRightUser() {
        Boolean valid = LoginActivity.isValidUsername("vergenieh");
        assertEquals("Check if username is valid.", true, valid);
    }

    @Test
    public void checkWrongUser() {
        Boolean valid = LoginActivity.isValidUsername("");
        assertEquals("Check if username is invalid.", false, valid);
    }

    @Test
    public void checkRightAdmin(){
        Boolean valid = LoginActivity.isValidAdministrator("admin", "admin");
        assertEquals("Check if administrator is valid.", true, valid);
    }

    @Test
    public void checkWrongAdmin() {
        Boolean valid = LoginActivity.isValidAdministrator("YakMessi", "BooMessi100%");
        assertEquals("Check if administrator is invalid.", false, valid);
    }
}
