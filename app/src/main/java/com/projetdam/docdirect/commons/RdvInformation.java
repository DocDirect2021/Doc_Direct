package com.projetdam.docdirect.commons;

import java.util.ArrayList;
import java.util.List;

public class RdvInformation {

    private ArrayList<String> nastedList;
    private boolean isExpandable;
    private String  jour;


    public RdvInformation() {
    }

    public RdvInformation(ArrayList<String> itemList, String jour) {
        this.nastedList = itemList;
        this.jour = jour;
        isExpandable = false;
    }

    public ArrayList<String> getNastedList() {
        return nastedList;
    }

    public void setNastedList(ArrayList<String> nastedList) {
        this.nastedList = nastedList;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }
}

