package com.projetdam.docdirect.commons;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RdvInformation {

    private boolean isExpandable;
    private String mykey;
    private Map<String, ArrayList<String>> myMap;


    public RdvInformation() {
    }


    public RdvInformation(String mykey, Map<String, ArrayList<String>> myMaps2) {
        this.myMap = myMaps2;
        this.mykey = mykey;
        this.isExpandable = false;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public Map<String, ArrayList<String>> getMyMap() {
        return myMap;
    }


    public String getMykey() {
        return mykey;
    }

}

