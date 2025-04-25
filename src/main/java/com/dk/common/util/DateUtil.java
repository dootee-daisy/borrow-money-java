package com.dk.common.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date addMonth(Date source,int months){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(source);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        String rate = "0.6";
        BigDecimal r = new BigDecimal(10600.00);
        System.out.println(r.floatValue());
        r = r.divide(new BigDecimal("12"),2,BigDecimal.ROUND_HALF_UP);
        System.out.println(r.floatValue());
    }
}
