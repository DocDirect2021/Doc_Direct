package com.projetdam.docdirect.commons;

import android.os.Build;
import android.text.style.LocaleSpan;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UtilsTimeSlot {
    private static final String TAG = "TEST TEST";
    private static LocalTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<String> getSlots(String startTime, String endTime, int duration) {
        ArrayList<String> hours = new ArrayList<>();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime t1 = LocalTime.parse(startTime, timeFormatter);
        LocalTime t2 = LocalTime.parse(endTime, timeFormatter);

        for (LocalTime t = t1; t.toSecondOfDay() < t2.toSecondOfDay(); t = t.plusMinutes(duration)) {
            hours.add(t.format(timeFormatter));
        }
        return hours;
    }
}
