package com.example.programare_examene.common.util;

public class Validators {
    public static boolean isNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isEmpty(String s) {
        return !isNotEmpty(s);
    }
}
