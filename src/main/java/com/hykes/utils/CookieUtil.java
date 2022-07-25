package com.hykes.utils;

import javax.servlet.http.Cookie;

public class CookieUtil {

    private CookieUtil() {}

    public static Cookie getCookie(Cookie[] cookies, String name){
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if(name != null && name.equals(cookie.getName())){
                    return cookie;
                }
            }
        }

        return null;
    }

}
