package com.example.work0905.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Validation methods of user input.
 *
 * Validation Example:
 * https://stackoverflow.com/questions/33072569/best-practice-input-validation-android
 *
 * Regular Expression (Regex) explanation:
 * https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
 **/

public class UserInputValidator {

    // Email regex pattern
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$;";

    // Password regex two patterns
    // word of characters (\w) count from 8 to 16 (including 8 and 16)
    //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String PASSWORD_PATTERN_SPECIAL_CHARS = "^[a-zA-Z@#$%]\\w{7,15}$";
    //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
    private static final String PASSWORD_PATTERN_NON_SPECIAL_CHARS = "^[a-zA-Z]\\w{7,15}$";


    public static boolean isValidEmail(String string){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public static boolean isValidPassword(String string, boolean allowSpecialChars){
        String PATTERN;
        if(allowSpecialChars){
            PATTERN = PASSWORD_PATTERN_SPECIAL_CHARS;
        }else{
            PATTERN = PASSWORD_PATTERN_NON_SPECIAL_CHARS;
        }

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }

    public boolean isNumeric(String string){
        return TextUtils.isDigitsOnly(string);
    }

    //Add more validators here if necessary

}
