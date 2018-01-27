package com.example.yuriy.multithredsorter.service.manager;


import android.util.Log;

import com.example.yuriy.multithredsorter.model.Mechanizm;
import com.example.yuriy.multithredsorter.sortUtils.MechanizmSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class TaskExecutor {

    private static final int THREAD_POOL_SIZE = 3;

    private TaskExecutor() {
    }


    public static void executeMultiTask(Mechanizm[][] mulyTaskData, ThreadManager.ThreadManagerCallback callback) {

        //define tasks callable list
        List<Callable<AdvancedTimeLinkedList<Mechanizm>>> tasks = new ArrayList<>();
        for (final Mechanizm[] singletaskData : mulyTaskData) {


            Callable<AdvancedTimeLinkedList<Mechanizm>> callable = new Callable<AdvancedTimeLinkedList<Mechanizm>>() {
                @Override
                public AdvancedTimeLinkedList<Mechanizm> call() throws Exception {
//                    int k = new Random().nextInt(5);
//                    int additionTaskTime = 1000 * k;
//                    Log.w("TaskExecutor", "taskTime =" + additionTaskTime);

                    //start
                    long lStartTime = System.nanoTime();
                    Mechanizm[] sortedDataArr = new MechanizmSorter().sort(singletaskData);
                    //end
                    long lEndTime = System.nanoTime();
                    long sortTime = lEndTime - lStartTime; // nano seconds
                    Log.e("TaskExecutor", "sort_time =" + sortTime);
//                   "Elapsed time in milliseconds: " + output / 1000000);
                    //                    Thread.sleep(additionTaskTime);
                    AdvancedTimeLinkedList<Mechanizm> sortedData =
                            new AdvancedTimeLinkedList<Mechanizm>(Arrays.asList(sortedDataArr));
                    sortedData.setSortingDeltaTime(sortTime);
                    return sortedData;
                }
            };
            tasks.add(callable);

        }

        ExecutorService exec = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<AdvancedTimeLinkedList<Mechanizm>> resultSortedData = new LinkedList<>();
        try {
            List<Future<AdvancedTimeLinkedList<Mechanizm>>> results = exec.invokeAll(tasks);
            for (Future<AdvancedTimeLinkedList<Mechanizm>> future : results) {
                AdvancedTimeLinkedList<Mechanizm> sortedSingleData = future.get();
                resultSortedData.add(sortedSingleData);
                for (Mechanizm mechanizm : sortedSingleData) {
                    Log.w("TaskExecutor", mechanizm.getName() + "id = " + mechanizm.getId());
                }

            }
            if (callback != null) {

                callback.onMultiTaskFinish(resultSortedData);
            } else {
                Log.e("TaskExecutor", "there was no ThreadManagerCallback registered");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            exec.shutdown();
        }
    }
}
