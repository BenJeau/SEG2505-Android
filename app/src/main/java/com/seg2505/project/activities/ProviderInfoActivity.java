package com.seg2505.project.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.seg2505.project.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProviderInfoActivity  extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_provider_info);
        }

        public static boolean isValidStreetNum(String streetNum) {
            if(streetNum.trim().isEmpty()) {
                return false;
            }
            try {
                Integer.parseInt(streetNum);
            } catch(NumberFormatException e) {
                return false;
            }
            return true;
        }

        public static boolean isValidStreetName(String streetName) {
            if(streetName.trim().isEmpty()) {
                return false;
            }
            for (int i = 0; i != streetName.length(); i++) {
                String chars = String.valueOf(streetName.charAt(i));
                if (!Character.isLetterOrDigit(streetName.charAt(i)) || !Character.isSpaceChar(streetName.charAt(i)) || chars != "-") {
                    return false;
                }
            }
            return true;
        }

        public static boolean isValidPostalCode(String postalCode) {
            if(postalCode.trim().isEmpty()) {
                return false;
            }
            if (postalCode.length() != 6) {
                return false;
            }
            for (int i = 0; i < postalCode.length(); i++) {
                if (i%2 == 0) {
                    if(!Character.isLetter(postalCode.charAt(i)) || !Character.isUpperCase(postalCode.charAt(i))) {
                        return false;
                    }
                } else {
                    if(!Character.isDigit(postalCode.charAt(i))) {
                        return false;
                    }
                }
            }
            return true;
        }

        public static boolean isValidName(String name) {
            for (int i = 0; i != name.length(); i++) {
                String chars = String.valueOf(name.charAt(i));
                if (!Character.isLetter(name.charAt(i)) || chars != "-") {
                    return false;
                }
            }
            return true;
        }

        public static boolean isValidCompanyName(String companyName) {
            if(companyName.trim().isEmpty()) {
                return false;
            }
            for (int i = 0; i != companyName.length(); i++) {
                String chars = String.valueOf(companyName.charAt(i));
                if (!Character.isLetter(companyName.charAt(i)) || !Character.isSpaceChar(companyName.charAt(i)) || chars != "-") {
                    return false;
                }
            }
            return true;
        }

        public static boolean isValidDescription(String description) {
            if(description.length() > 300) {
                return false;
            }
            return true;
        }

        public static boolean isValidPhoneNumber(String phoneNumber) {
            if(phoneNumber.trim().isEmpty()) {
                return false;
            }
            if(phoneNumber.length() > 12) {
                return false;
            }
            for(int i = 0; i < 3;i++) {
                if(!Character.isDigit(phoneNumber.charAt(i))) {
                    return false;
                }
            }
            String chars = String.valueOf(phoneNumber.charAt(3));
            if (chars != "-") {
                return false;
            }
            String chars1 = String.valueOf(phoneNumber.charAt(7));
            if (chars != "-") {
                return false;
            }
            for(int i = 4; i < 7; i++) {
                if(!Character.isDigit(phoneNumber.charAt(i))) {
                    return false;
                }
            }
            for(int i = 8; i < phoneNumber.length(); i++) {
                if(!Character.isDigit(phoneNumber.charAt(i))) {
                    return false;
                }
            }
            return true;
        }


}
