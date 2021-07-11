package com.dogfeeder;

import android.text.TextUtils;

public class LastFedContainer{
    private String lastFed;

    public LastFedContainer(String lastFed) {
        this.lastFed = lastFed;
    }

    public LastFedContainer() {
    }

    public String getLastFed() {
        return lastFed;
    }

    public void setLastFed(String lastFed) {
        this.lastFed = lastFed;
    }
}