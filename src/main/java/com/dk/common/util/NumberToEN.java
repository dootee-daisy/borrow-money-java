package com.dk.common.util;

import java.math.BigDecimal;

// Convert numbers to uppercase English monetary representation (e.g., "One Hundred Dollars")
public class NumberToEN {

    // Uppercase English numbers
    private static final String[] EN_UPPER_NUMBER = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" };
    // Uppercase English currency units:
    // Cent (1/100 Dollar), Dime (1/10 Dollar), Dollar (base unit),
    // Ten, Hundred, Thousand, Ten Thousand, Hundred Thousand, Million, Ten Million,
    // Hundred Million, Billion, Ten Billion, Hundred Billion, Trillion, Ten Trillion,
    // Hundred Trillion, Quadrillion
    private static final String[] EN_UPPER_MONETARY_UNIT = { "Cent", "Dime", "Dollar",
        "Ten", "Hundred", "Thousand", "Ten Thousand", "Hundred Thousand", "Million",
        "Ten Million", "Hundred Million", "Billion", "Ten Billion", "Hundred Billion",
        "Trillion", "Ten Trillion", "Hundred Trillion", "Quadrillion" };
    // Special character: Whole
    private static final String EN_FULL = "Whole";
    // Special character: Negative
    private static final String EN_NEGATIVE = "Negative";
    // Precision of the amount, default value is 2
    private static final int MONEY_PRECISION = 2;
    // Special character: Zero Dollar Whole
    private static final String EN_ZERO_FULL = "Zero Dollar " + EN_FULL;

    /**
     * Convert the input amount to uppercase English monetary representation
     * @param numberOfMoney The input amount
     * @return The corresponding uppercase English representation
     */
    public static String number2ENMonetaryUnit(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or positive.
        int signum = numberOfMoney.signum();
        // Case of zero dollar whole
        if (signum == 0) {
            return EN_ZERO_FULL;
        }
        // The amount will be rounded here
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
            .setScale(0, 4).abs().longValue();
        // Get the last two digits after the decimal point
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // Check the last two digits, there are four cases: 00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // Get the last digit each time
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, " " + EN_UPPER_MONETARY_UNIT[6] + " ");
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, " " + EN_UPPER_MONETARY_UNIT[10] + " ");
                }
                sb.insert(0, " " + EN_UPPER_MONETARY_UNIT[numIndex] + " ");
                sb.insert(0, EN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, EN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, " " + EN_UPPER_MONETARY_UNIT[numIndex] + " ");
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, " " + EN_UPPER_MONETARY_UNIT[numIndex] + " ");
                }
                getZero = true;
            }
            // Remove the last digit from number each time
            number = number / 10;
            ++numIndex;
        }
        // If signum == -1, it means the input number is negative, append the special character: Negative
        if (signum == -1) {
            sb.insert(0, EN_NEGATIVE + " ");
        }
        // If the last two digits after the decimal point are "00", append the special character: Whole
        if (!(scale > 0)) {
            sb.append(" " + EN_FULL);
        }
        return sb.toString().trim();
    }
}