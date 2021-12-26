package com.projetdam.docdirect.commons;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UtilsTimeSlotTest {

    @Test
    public void createSlots() {
        ArrayList<String> oo = new ArrayList<>();
        Assert.assertEquals(oo, UtilsTimeSlot.createSlots("9:30", "12:30", 20));
    }

    @Test
    public void formatHeure() {
    }

    @Test
    public void saveRdv() {
    }

    @Test
    public void annulRdv() {
    }
}