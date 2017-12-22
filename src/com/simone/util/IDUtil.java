package com.simone.util;

import java.util.UUID;

/**
 * Created by dllo on 17/12/19.
 */
public class IDUtil {
    public static String randomId(){
        return UUID.randomUUID().toString().replace("-","")
                .toLowerCase();
    }
    public static boolean isEmpty(String s){
        return s==null||s.trim().isEmpty();
    }
}
