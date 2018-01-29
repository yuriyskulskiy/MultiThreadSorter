package com.example.yuriy.multithreadsorter.service.manager;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static com.example.yuriy.multithreadsorter.Constants.BUBBLE_SORT_TYPE;
import static com.example.yuriy.multithreadsorter.Constants.QUICK_SORT_TYPE;


public class AdvancedDataLinkedList<E> extends ArrayList<E> implements Serializable {

    private long sortingDeltaTime;
    private int sortingTypeMethod;

    public AdvancedDataLinkedList(@NonNull Collection<? extends E> c) {
        super(c);
    }

    public long getSortingDeltaTime() {
        return sortingDeltaTime;
    }

    public void setSortingDeltaTime(long sortingDeltaTime) {
        this.sortingDeltaTime = sortingDeltaTime;
    }

    public int getSortingTypeMethod() {
        return sortingTypeMethod;
    }

    public void setSortingTypeMethod(int sortingTypeMethod) {
        this.sortingTypeMethod = sortingTypeMethod;
    }

    public String extractSortingTypeName() {
        switch (sortingTypeMethod) {
            case BUBBLE_SORT_TYPE:
                return "Bubble sort";
            case QUICK_SORT_TYPE:
                return "Quick sort";
            default:
                throw new UnsupportedOperationException("undefined sort type method");
        }
    }
}
