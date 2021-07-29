package com.rifeng.p2p.util;

import android.text.Editable;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kegh on 2018/2/7.
 */

public class StringUtils {
    public static boolean isNullOrEmpty(String tk) {
        return tk == null || tk.trim().length() == 0;
    }

    public static boolean isNullOrEmpty(Editable tk) {
        return tk == null || tk.toString().trim().length() == 0;
    }

    public static boolean isNullOrEmpty(CharSequence tk) {
        return tk == null || tk.toString().trim().length() == 0;
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isMobile(String str) {
        boolean b = false;
        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static String wrapString(String tk) {
        return tk == null ? "" : tk;
    }

    // 判断是否为纯数字;
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    // 判断是否是数字
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern.compile("^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}?$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static String formatFileSize(long fileSize) {
        if (fileSize < 1024) {
            return fileSize + "B";
        } else if (fileSize < 1024 * 1024) {// 小于1M显示K
            return fileSize / 1024 + "K";
        } else {// 大于1M显示M
            return (fileSize / 1024 / 1024) + "M";
        }
    }

    public static boolean checkEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        } else {
            return email.matches("^[a-zA-Z0-9_\\.-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        }
    }

    /**
     * 判断是否为字母
     *
     * @param fstrData
     * @return
     */
    public static boolean check(String fstrData) {
        char c = fstrData.charAt(0);
        return (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')));
    }

    public static String formatVideoFileSize(long fileSize) {
        if (fileSize < 1024) {
            return fileSize + "B";
        } else if (fileSize < 1024 * 1024) {// 小于1M显示K
            DecimalFormat df = new DecimalFormat("###.0");
            return df.format(fileSize / 1024f) + "KB";
        } else {// 大于1M显示M
            DecimalFormat df = new DecimalFormat("###.0");
            return df.format(fileSize * 10 * 0.1 / 1024 / 1024) + "MB";
        }
    }


    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static boolean isEmpty(String token) {
        if (token == null || token.length() == 0) {
            return true;
        }
        return false;
    }


    public static String compareRule(String password) {
        if (StringUtils.isEmpty(password)) {
            return "password Can not be empty";
        } else {
            boolean isTrue = password.matches("^.*[\\s]+.*$");
            if (isTrue) {
                return "password can not contain blank space";
            } else {
                isTrue = password.matches("^.*[a-zA-Z]+.*$");
                if (!isTrue) {
                    return "Password regular error,Password must contain letters and numbers";
                } else {
                    isTrue = password.matches("^.{8,}$");
                    return !isTrue ? "Password regular error,Length must longer than 8" : null;
                }
            }
        }
    }

    public static boolean checkPassword(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;

        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            }
            if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            }
            if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }

            if (numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }

        return false;
    }

}
