package com.ienh.cpi.cpi.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // encrypt password with BCrypt
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // compare password with BCrypt
    public static boolean compare(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public static boolean validPassword(String passString) {
        boolean valid = true;
        if (passString.length() < 8)
            valid = false;
        return valid;
    }

}
