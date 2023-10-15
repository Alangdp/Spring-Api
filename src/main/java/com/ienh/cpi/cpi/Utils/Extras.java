package com.ienh.cpi.cpi.Utils;

public class Extras {

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String generateToken() {
        String token = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 4; i++) {
            token += characters.charAt((int) Math.floor(Math.random() * characters.length()));
        }

        String nanoTime = String.valueOf(System.nanoTime());
        token += nanoTime.substring(nanoTime.length() - 6);

        return token;
    }
}
