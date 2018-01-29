package com.example.yuriy.multithreadsorter.service.manager;


import android.util.Log;

import com.example.yuriy.multithreadsorter.model.Mechanizm;
import com.example.yuriy.multithreadsorter.sortUtils.BaseSortedClass;
import com.example.yuriy.multithreadsorter.sortUtils.BubbleSort;
import com.example.yuriy.multithreadsorter.sortUtils.QuickSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.example.yuriy.multithreadsorter.Constants.BUBBLE_SORT_TYPE;
import static com.example.yuriy.multithreadsorter.Constants.QUICK_SORT_TYPE;

class TaskExecutor {

    private static final int THREAD_POOL_SIZE = 3;

    private TaskExecutor() {
    }


    public static void executeMultiTask(Mechanizm[][] mulyTaskData,
                                        ThreadManager.ThreadManagerCallback callback,
                                        final int selectedSortType) {

        //define tasks callable list
        List<Callable<AdvancedDataLinkedList<Mechanizm>>> tasks = new ArrayList<>();
        for (final Mechanizm[] singletaskData : mulyTaskData) {


            Callable<AdvancedDataLinkedList<Mechanizm>> callable = new Callable<AdvancedDataLinkedList<Mechanizm>>() {
                @Override
                public AdvancedDataLinkedList<Mechanizm> call() throws Exception {
//                    int k = new Random().nextInt(5);
//                    int additionTaskTime = 1000 * k;
//                    Log.w("TaskExecutor", "taskTime =" + additionTaskTime);
                    BaseSortedClass<Mechanizm> sortExecutor;
                    switch (selectedSortType) {
                        case BUBBLE_SORT_TYPE:
                            sortExecutor = new BubbleSort();
                            break;
                        case QUICK_SORT_TYPE:
                            sortExecutor = new QuickSort();
                            break;
                        default:
                            throw new UnsupportedOperationException();
                    }
                    //get sort type switch

//                    BaseSortedClass<Mechanizm> sortExecutor = new BubbleSort();
//                    BaseSortedClass<Mechanizm> sortExecutor = new QuickSort();

                    //start
                    long lStartTime = System.nanoTime();
                    Mechanizm[] sortedDataArr = sortExecutor.sort(singletaskData);
                    //end
                    long lEndTime = System.nanoTime();
                    long sortTime = lEndTime - lStartTime; // nano seconds
                    Log.e("TaskExecutor", "sort_time =" + sortTime);
//                   "Elapsed time in milliseconds: " + output / 1000000);
                    //                    Thread.sleep(additionTaskTime);
                    AdvancedDataLinkedList<Mechanizm> sortedData =
                            new AdvancedDataLinkedList<Mechanizm>(Arrays.asList(sortedDataArr));
                    sortedData.setSortingDeltaTime(sortTime);
                    sortedData.setSortingTypeMethod(selectedSortType);
                    return sortedData;
                }
            };
            tasks.add(callable);

        }

        ExecutorService exec = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<AdvancedDataLinkedList<Mechanizm>> resultSortedData = new LinkedList<>();
        try {
            List<Future<AdvancedDataLinkedList<Mechanizm>>> results = exec.invokeAll(tasks);
            for (Future<AdvancedDataLinkedList<Mechanizm>> future : results) {
                AdvancedDataLinkedList<Mechanizm> sortedSingleData = future.get();
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
