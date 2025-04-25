package com.dk.common.util;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IDGenerator {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    private static final String ID_PREFIX_MEMBER = "MD"; // Member module
    private static final String ID_PREFIX_ORDER = "OD"; // Order module
    private static final String ID_PREFIX_USER = "UD"; // User module
    private static final String ID_PREFIX_SYS = "SD"; // System module

    public static String createMemberId() {
        return createId(ID_PREFIX_MEMBER);
    }

    public static String createOrderId() {
        return createId(ID_PREFIX_ORDER);
    }

    public static String createUserId() {
        return createId(ID_PREFIX_USER);
    }

    public static String createSysId() {
        return createId(ID_PREFIX_SYS);
    }

    private static synchronized String createId(String prefix) {
        prefix = StringUtils.trimAllWhitespace(prefix);
        if (StringUtils.isEmpty(prefix) || prefix.length() != 2) {
            return null;
        }
        // Add 8-digit date, 10 digits remaining
        prefix = prefix + format.format(new Date());
        // Add 5-digit timestamp, 5 digits remaining
        String currentTimeMillis = System.currentTimeMillis() + "";
        prefix = prefix + currentTimeMillis.substring(currentTimeMillis.length() - 5, currentTimeMillis.length());
        // Add 5-digit random number
        prefix = prefix + (int) ((Math.random() * 9 + 1) * 10000);
        return prefix.substring(6, prefix.length());
    }

    public static synchronized String createId() {
        String prefix = "";
        prefix = prefix + format.format(new Date());
        // Add 5-digit timestamp, 5 digits remaining
        String currentTimeMillis = System.currentTimeMillis() + "";
        prefix = prefix + currentTimeMillis.substring(currentTimeMillis.length() - 5, currentTimeMillis.length());
        // Add 5-digit random number
        prefix = prefix + (int) ((Math.random() * 9 + 1) * 10000);
        return prefix;
    }

    private static String getRandomString(int length, String base) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomNumbersAndUppercaseString(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        return getRandomString(length, base);
    }
}