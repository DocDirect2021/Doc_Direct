package com.projetdam.docdirect.commons;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ModelDayPlannerTest {
    ArrayList<String> outList;
    int total;
    LocalDate today = LocalDate.now();

    ArrayList<ModelDayPlanner> daySlots = new ArrayList<>();
    HashMap<LocalDate, ArrayList<String>> outSlots = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        outList = new ArrayList<>(Arrays.asList("09:30", "10:00", "10:30", "11:30"));
    }

    @Test
    public void getSlots() {
        ModelDayPlanner dayPlanner
                = new ModelDayPlanner(today, "09:00", "18:10", 30, outList, true);
        System.out.println(outList.contains("10:00"));
        ArrayList<String> h = dayPlanner.getOpenSlots();
        assertArrayEquals(new ArrayList[]{outList}, new ArrayList[]{dayPlanner.getOpenSlots()});
    }

    @Test
    public void getDisplayDate() {
    }
}