package com.dogfeeder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class FoodTimeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        new NotificationHelper(context, "Food Alarm", "It's time to feed your dog.");
    }
}
