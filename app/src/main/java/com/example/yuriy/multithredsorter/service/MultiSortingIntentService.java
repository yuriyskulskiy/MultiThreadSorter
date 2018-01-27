package com.example.yuriy.multithredsorter.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.yuriy.multithredsorter.Constants;
import com.example.yuriy.multithredsorter.model.Mechanizm;
import com.example.yuriy.multithredsorter.service.manager.AdvancedTimeLinkedList;
import com.example.yuriy.multithredsorter.service.manager.ThreadManager;

import java.io.Serializable;
import java.util.List;


public class MultiSortingIntentService extends IntentService implements ThreadManager.ThreadManagerCallback {
    private static final String MULTI_TASK_EXTRA = "com.example.yuriy.multythredsorter.task_extra";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("QQQ", "onCreate()");
    }

    public MultiSortingIntentService() {
        super("MultiSortingIntentService");
    }

    //, List<Parcelable[]> multiTask,
    public static void startMultiTask(Context context, Mechanizm[][] multiTaskData) {

        Intent intent = new Intent(context, MultiSortingIntentService.class);

        intent.putExtra(MULTI_TASK_EXTRA, multiTaskData);
//        intent.putExtra(MULTI_TASK_EXTRA, singleTask);

        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Mechanizm[][] multiTaskList = (Mechanizm[][]) intent.getSerializableExtra(MULTI_TASK_EXTRA);

            ThreadManager.Builder builder = ThreadManager.newBuilder();
            ThreadManager manager = builder
                    .setMultiTask(multiTaskList)
                    .registerOnFinishCallback(this)
                    .build();
            manager.executeMultiTask();

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w("Service", "onDestroy");
    }

    @Override
    public void onMultiTaskFinish(List<AdvancedTimeLinkedList<Mechanizm>> sortedMultiTaskData) {
        Intent localIntent =
                new Intent(Constants.BROADCAST_SORT_ACTION);
        Log.w("Service", "onMultiTaskFinish: data size = " + sortedMultiTaskData.size());

        localIntent.putExtra(Constants.RESULT_SORTED_DATA, (Serializable) sortedMultiTaskData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}


