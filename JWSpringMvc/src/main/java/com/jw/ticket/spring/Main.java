package com.jw.ticket.spring;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String str = "com.cjw.jd";
        String replace = str.replaceAll("\\.", "/");
        System.out.println(replace);
        String str1 = "user//query";
        final String s = str1.replaceAll("/+", "/");
        System.out.println(s);
        String value = Arrays.toString(new String[]{"1", "2"}).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
        System.out.println(value);

        final String userServiceImpl = toLowerFirstWord("UserServiceImpl");
        System.out.println(userServiceImpl);
    }

    private static String toLowerFirstWord(String name) {

        char[] charArray = name.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }


}
