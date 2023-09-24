package com.example.mobile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class valid_data {
    public static boolean isEmail(String email){
        boolean returnValue = false;
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()){
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isPasswordValid(String password) {
        // 비밀번호에 대문자, 소문자, 특수 문자가 각각 하나 이상 포함되어야 합니다.
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
