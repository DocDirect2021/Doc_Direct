package com.projetdam.docdirect.commons;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.util.ArrayList;

public class TimeRange {
    private static final String TAG = "TEST TEST";
    private static LocalTime time;

//    private static ModelTimeRange timeRange;
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void test() {
//        time = LocalTime.of(9, 45);
//        timeRange = new ModelTimeRange(time, time.plusMinutes(20));
//        Log.i(TAG, "test: " + timeRange.getStartTime().toSecondOfDay());
//        Log.i(TAG, "test: " + timeRange.getEndTime().toSecondOfDay());
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<LocalTime> edt(LocalTime time1, LocalTime time2) {
        ArrayList<LocalTime> hours = new ArrayList<>();

        for (LocalTime t = time1; t.toSecondOfDay() < time2.toSecondOfDay(); t = t.plusMinutes(20)) {
            hours.add(t);
//            Log.i(TAG, "edt: " + t);
        }
        return hours;
    }
}
