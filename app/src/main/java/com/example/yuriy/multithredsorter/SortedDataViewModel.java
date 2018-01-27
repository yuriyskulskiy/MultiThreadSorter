package com.example.yuriy.multithredsorter;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import com.example.yuriy.multithredsorter.model.Mechanizm;
import com.example.yuriy.multithredsorter.service.manager.AdvancedTimeLinkedList;

import java.util.List;

public class SortedDataViewModel extends ViewModel {
    private MutableLiveData<List<AdvancedTimeLinkedList<Mechanizm>>> loadedSortedData;


    public LiveData<List<AdvancedTimeLinkedList<Mechanizm>>> getLoadedSortedData() {
        if (loadedSortedData == null) {
            loadedSortedData = new MutableLiveData<List<AdvancedTimeLinkedList<Mechanizm>>>();
        }
        return loadedSortedData;
    }
    //use this method only from the main thread   (Thread.currentThread() == Looper.getMainLooper().getThread()) = true
    public void saveSortedDataToVM(List<AdvancedTimeLinkedList<Mechanizm>> newSortedData) {
        loadedSortedData.setValue(newSortedData);
    }
}


