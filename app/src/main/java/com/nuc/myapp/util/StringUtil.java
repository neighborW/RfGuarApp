package com.nuc.myapp.util;

public class StringUtil {

    public static boolean isEmpty(String str){

        if (str == null || str.length() <= 0){

            return true ;
        }
        else {
            return false;
        }
    }

}
