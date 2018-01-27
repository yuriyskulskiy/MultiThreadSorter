package com.example.yuriy.multithredsorter.receiver;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;


public abstract class LocalBroadcastReceiver extends BasicBroadcastReceiver {

    public LocalBroadcastReceiver(String... actions) {
        super(actions);
    }

    @Override
    public void register(Context context) {
        LocalBroadcastManager.getInstance(context).registerReceiver(this, mIntentFilter);
    }

    @Override
    public void unregister(Context context) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
    }
}
