package com.training.client.string;

public class UserStringService {
    
    public String concatenate(String... strings) {
        return String.join(", ", strings);
    }

    public String format(String template, Object... args) {
        return String.format(template, args);
    }

    public boolean containsIgnoreCase(String str, String search) {
        return str.toLowerCase().contains(search.toLowerCase());
    }

    public String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String padLeft(String str, int length, char padChar) {
        return String.format("%" + length + "s", str).replace(' ', padChar);
    }
}