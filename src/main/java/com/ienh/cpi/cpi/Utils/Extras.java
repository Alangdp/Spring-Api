package com.ienh.cpi.cpi.Utils;

public class Extras {

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    // Capitalizes the first letter of a string
    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    // generate Token with 10 characters add microsecond to the end
    public static String generateToken() {
        String token = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 6; i++) {
            token += characters.charAt((int) Math.floor(Math.random() * characters.length()));
        }

        // nanotime with 3 digits
        token += String.valueOf(System.nanoTime()).substring(10);
        return token;
    }
}
