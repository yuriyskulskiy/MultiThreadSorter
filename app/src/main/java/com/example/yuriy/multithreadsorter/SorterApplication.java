package com.example.yuriy.multithreadsorter;

import android.app.Application;

import com.example.yuriy.multithreadsorter.incommingData.TaskGeneratorImpl;


public class SorterApplication extends Application {

    public void onCreate() {
        super.onCreate();
        TaskGeneratorImpl.init(this);
    }
}
