package com.projetdam.docdirect.commons;

import java.util.ArrayList;

public class ModelDaySlots {
    private String date;
    private ArrayList<String> slots;

    public ModelDaySlots(String date, ArrayList<String> slots) {
        this.date = date;
        this.slots = slots;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<String> slots) {
        this.slots = slots;
    }
}
