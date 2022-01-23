package com.projetdam.docdirect.commons;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.Timestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HelperTime {
    private static final String TAG = "TEST TEST";

    private static final ZoneOffset zoneOffset = ZoneOffset.of("+01:00");

    public static LocalDateTime getDateTime(Timestamp timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp.getSeconds(), 0, zoneOffset);
    }

    public static Timestamp getTimestamp(LocalDateTime dateTime) {
        return new Timestamp(dateTime.toEpochSecond(zoneOffset), 0);
    }

    public static String formatHeure(LocalTime h) {
        String hh = String.format("%02d", h.getHour());
        String mm = String.format("%02d", h.getMinute());
        return hh + ":" + mm;
    }

}
