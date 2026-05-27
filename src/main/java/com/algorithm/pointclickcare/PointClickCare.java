package com.algorithm.pointclickcare;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PointClickCare {
    public static String MostFreeTime(String[] strArr) {
        // Parse events and convert to minutes
        List<Event> events = new ArrayList<>();

        for (String eventStr : strArr) {
            String[] times = eventStr.split("-");
            int start = timeToMinutes(times[0]);
            int end = timeToMinutes(times[1]);
            events.add(new Event(start, end));
        }

        // Sort events by start time
        events.sort((a, b) -> a.start - b.start);

        // Find the longest gap between consecutive events
        int maxFreeTime = 0;

        for (int i = 0; i < events.size() - 1; i++) {
            int currentEnd = events.get(i).end;
            int nextStart = events.get(i + 1).start;
            int freeTime = nextStart - currentEnd;

            if (freeTime > maxFreeTime) {
                maxFreeTime = freeTime;
            }
        }

        return minutesToTime(maxFreeTime);
    }

    // Helper function to convert time string to minutes since midnight
    private static int timeToMinutes(String timeStr) {
        Pattern pattern = Pattern.compile("(\\d+):(\\d+)(AM|PM)");
        Matcher matcher = pattern.matcher(timeStr);

        if (matcher.find()) {
            int hours = Integer.parseInt(matcher.group(1));
            int minutes = Integer.parseInt(matcher.group(2));
            String period = matcher.group(3);

            // Convert to 24-hour format
            if (period.equals("PM") && hours != 12) {
                hours += 12;
            } else if (period.equals("AM") && hours == 12) {
                hours = 0;
            }

            return hours * 60 + minutes;
        }

        return 0;
    }

    // Helper function to convert minutes back to hh:mm format
    private static String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

    public static void main(String[] args) {
        System.out.println(MostFreeTime(new String[] {
                "10:00AM-12:30PM", "02:00PM-02:45PM", "09:10AM-09:50AM"
        })); // Expected: 01:30

        System.out.println(MostFreeTime(new String[] {
                "12:15PM-02:00PM", "09:00AM-10:00AM", "10:30AM-12:00PM"
        })); // Expected: 00:30

        System.out.println(MostFreeTime(new String[] {
                "12:15PM-02:00PM", "09:00AM-12:11PM", "02:02PM-04:00PM"
        })); // Expected: 00:04
    }
}
