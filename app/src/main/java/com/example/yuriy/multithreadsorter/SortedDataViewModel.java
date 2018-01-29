package com.example.yuriy.multithreadsorter;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import com.example.yuriy.multithreadsorter.model.Mechanizm;
import com.example.yuriy.multithreadsorter.service.manager.AdvancedDataLinkedList;

import java.util.List;

public class SortedDataViewModel extends ViewModel {
    private MutableLiveData<List<AdvancedDataLinkedList<Mechanizm>>> loadedSortedData;


    public LiveData<List<AdvancedDataLinkedList<Mechanizm>>> getLoadedSortedData() {
        if (loadedSortedData == null) {
            loadedSortedData = new MutableLiveData<List<AdvancedDataLinkedList<Mechanizm>>>();
        }
        return loadedSortedData;
    }
    //use this method only from the main thread   (Thread.currentThread() == Looper.getMainLooper().getThread()) = true
    public void saveSortedDataToVM(List<AdvancedDataLinkedList<Mechanizm>> newSortedData) {
        loadedSortedData.setValue(newSortedData);
    }
}


