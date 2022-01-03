package com.projetdam.docdirect.commons;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class ModelDayPlanner {
    private LocalDate date;
    private ArrayList<String> slots;

    public ModelDayPlanner(LocalDate date, ArrayList<String> slots) {
        this.date = date;
        this.slots = slots;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<String> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<String> slots) {
        this.slots = slots;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDisplayDate() {
        String day = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE);
        return day + " " + date.getDayOfMonth() + " " + month;
    }
}
