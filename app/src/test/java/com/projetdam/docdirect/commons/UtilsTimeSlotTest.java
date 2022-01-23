package com.projetdam.docdirect.commons;

import static org.junit.Assert.*;

import com.google.firebase.Timestamp;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class UtilsTimeSlotTest {

    @Test
    public void testFormatHeure() {
        LocalTime h = LocalTime.now();
        assertEquals(h.toString(), HelperTime.formatHeure(h));
    }

    @Test
    public void saveRdv() {
    }

    @Test
    public void annulRdv() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getDateTime() {
        Timestamp t = Timestamp.now();
        LocalDateTime h = LocalDateTime.now();
        assertEquals(h, HelperTime.getDateTime(t));
    }

    @Test
    public void getTimestamp() {
        Timestamp t = Timestamp.now();
        LocalDateTime h = LocalDateTime.now();
        assertEquals(t, HelperTime.getTimestamp(h));
    }

}