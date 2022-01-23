package com.projetdam.docdirect;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.firebase.Timestamp;
import com.projetdam.docdirect.commons.HelperTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HashMapTest {
    @Test
    public void goTest() {
        ArrayList<Timestamp> list = new ArrayList<>();
        list.add(HelperTime.
                getTimestamp(LocalDateTime.of(2020, 3, 9, 15, 5)));
        list.add(HelperTime.
                getTimestamp(LocalDateTime.of(2020, 3, 9, 11, 15)));
        list.add(HelperTime.
                getTimestamp(LocalDateTime.of(2020, 5, 15, 12, 25)));
        list.add(HelperTime.
                getTimestamp(LocalDateTime.of(2020, 5, 20, 11, 35)));
        list.add(HelperTime.
                getTimestamp(LocalDateTime.of(2020, 5, 20, 11, 45)));
        int total = 3;

        LocalDate may10 = LocalDate.of(2020, Month.MAY, 20);
        HashMap<LocalDate, ArrayList<String>> outSlots = new HashMap<>();

        LocalDate lastDate = LocalDate.of(2020, 1, 1);

        for (Timestamp t : list) {
            LocalDateTime dateTime = HelperTime.getDateTime(t);
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            if (date.isAfter(lastDate)) {
                outSlots.put(date, new ArrayList<>());
                lastDate = date;
            }
            outSlots.get(date).add(time.toString());
        }

        for (Map.Entry<LocalDate, ArrayList<String>> e : outSlots.entrySet()) {
            ArrayList<String> l = e.getValue();
            System.out.println("outSlots: " + e.getKey());
            for (String h : l) {
                System.out.println("     " + h);
            }
        }

        assertEquals(total, outSlots.size());
    }
}
