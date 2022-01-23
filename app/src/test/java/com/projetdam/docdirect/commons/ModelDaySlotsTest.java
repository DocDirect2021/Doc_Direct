package com.projetdam.docdirect.commons;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class ModelDaySlotsTest {

    @Test
    public void getDate() {
    }

    @Test
    public void setDate() {
    }

    @Test
    public void getSlots() {
    }

    @Test
    public void setSlots() {
        LocalDate date = LocalDate.of(2022, 1, 3);
        ArrayList<String> slots = new ArrayList<>(); //HelperTime.createSlots();
        ModelDayPlanner slots1 = new ModelDayPlanner(date, "9:00", "15:30", 20, slots);
        assertEquals(date, slots1.getDate());
    }
}