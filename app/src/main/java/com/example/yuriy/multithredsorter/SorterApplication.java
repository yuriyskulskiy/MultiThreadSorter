package com.example.yuriy.multithredsorter;

import android.app.Application;

import com.example.yuriy.multithredsorter.incommingData.TaskGeneratorImpl;


public class SorterApplication extends Application {

    public void onCreate() {
        super.onCreate();
        TaskGeneratorImpl.init(this);
    }
}
