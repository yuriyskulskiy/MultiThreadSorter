package com.example.yuriy.multithreadsorter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;


public abstract class BasicBroadcastReceiver extends BroadcastReceiver {

    protected final IntentFilter mIntentFilter;

    public BasicBroadcastReceiver(String... actions) {

        mIntentFilter = new IntentFilter();
        for (String action : actions) {
            mIntentFilter.addAction(action);
        }
    }

    public abstract void register(Context context);

    public abstract void unregister(Context context);
}
