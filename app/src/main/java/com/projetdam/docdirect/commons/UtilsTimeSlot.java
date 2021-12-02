package com.projetdam.docdirect.commons;

import android.os.Build;
import android.text.style.LocaleSpan;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static ArrayList<String> getSl(String startTime, String endTime, int duration) {
//
//
//        ArrayList<String> days = new ArrayList<>();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate today = LocalDate.now();     //Today
//
//        for (int i= 0; i<7;i++) {
//            today.add();
//        }
//        return days;
//    }
}
