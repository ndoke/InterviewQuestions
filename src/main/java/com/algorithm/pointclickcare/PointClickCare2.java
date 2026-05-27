package com.algorithm.pointclickcare;

import java.util.Arrays;

public class PointClickCare2 {
    public static String MostFreeTime(String[] strArr) {
        int[] times = new int[strArr.length * 2];

        for (int i = 0; i < strArr.length; i++) {
            String[] parts = strArr[i].split("-");
            times[i * 2] = toMinutes(parts[0]);
            times[i * 2 + 1] = toMinutes(parts[1]);
        }

        Arrays.sort(times);

        int max = 0;
        for (int i = 1; i < times.length - 1; i += 2) {
            max = Math.max(max, times[i + 1] - times[i]);
        }

        return String.format("%02d:%02d", max / 60, max % 60);
    }

    private static int toMinutes(String time) {
        int h = Integer.parseInt(time.substring(0, time.indexOf(':')));
        int m = Integer.parseInt(time.substring(time.indexOf(':') + 1, time.length() - 2));
        boolean pm = time.endsWith("PM");

        if (pm && h != 12) h += 12;
        if (!pm && h == 12) h = 0;

        return h * 60 + m;
    }

    public static void main(String[] args) {
        System.out.println(MostFreeTime(new String[] {
                "10:00AM-12:30PM", "02:00PM-02:45PM", "09:10AM-09:50AM"
        })); // 01:30

        System.out.println(MostFreeTime(new String[] {
                "12:15PM-02:00PM", "09:00AM-10:00AM", "10:30AM-12:00PM"
        })); // 00:30

        System.out.println(MostFreeTime(new String[] {
                "12:15PM-02:00PM", "09:00AM-12:11PM", "02:02PM-04:00PM"
        })); // 00:04
    }
}
