package com.chatfuel.elevator.demo.utils;

public class MiscUtils {

    private MiscUtils() {
    }

    public static String getOrdinalNumberEnding(int n) {
        try {
            String result = "";
            if (n > 3 && n < 21) result = n + "th";
            else if ((n % 100) > 10 && (n % 100) < 20) result += n + "th";
            else if (n % 10 == 1) result = n + "st";
            else if (n % 10 == 2) result = n + "nd";
            else if (n % 10 == 3) result = n + "rd";
            else result = n + "th";
            return result;
        } catch (NumberFormatException e) {
            return n + "th";
        }
    }
}
