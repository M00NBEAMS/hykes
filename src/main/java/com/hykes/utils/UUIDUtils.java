package com.hykes.utils;

import java.util.UUID;

public class UUIDUtils {
    private UUIDUtils() { }

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
