package com.projetdam.docdirect.commons;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ModelDayPlanner {
    private LocalDate date;
    private LocalTime fromHour;         // à partir de maintenant si aujourd'hui
    private LocalTime openHour;         // début journée
    private LocalTime closeHour;        // fin journée
    private int duration;               // durée consulation
    private ArrayList<String> offSlots; // créneaux indisponibles
    private ArrayList<String> openSlots;

    public ModelDayPlanner(LocalDate date, String openHour, String closeHour, int duration, ArrayList<String> offSlots) {
        this(date, openHour, closeHour, duration, offSlots, false);
    }

    public ModelDayPlanner(LocalDate date, String openHour, String closeHour, int duration, ArrayList<String> offSlots, boolean isFromNow) {
        this.date = date;
        this.openHour = LocalTime.parse(openHour);
        this.closeHour = LocalTime.parse(closeHour);
        this.duration = duration;
        this.offSlots = offSlots;
        this.fromHour = (isFromNow) ? LocalTime.now() : LocalTime.of(0, 0);
    }

    private void generateSlots() {
        openSlots = new ArrayList<>();
        for (LocalTime t = openHour; t.isBefore(closeHour); t = t.plusMinutes(duration)) {
            if (t.isBefore(fromHour)) continue;
            String h = t.toString();
            if (offSlots == null || !offSlots.contains(h))
                openSlots.add(h);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<String> getOpenSlots() {
        if (openSlots == null) generateSlots();
        return openSlots;
    }

    public void setOpenSlots(ArrayList<String> openSlots) {
        this.openSlots = openSlots;
    }

    public String getDisplayDate() {
        String day = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE);
        return day + " " + date.getDayOfMonth() + " " + month;
    }
}
