package com.example.yuriy.multithredsorter.service.manager;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class AdvancedTimeLinkedList<E> extends ArrayList<E> implements Serializable {

    private long sortingDeltaTime;

    public AdvancedTimeLinkedList(@NonNull Collection<? extends E> c) {
        super(c);
    }

    public long getSortingDeltaTime() {
        return sortingDeltaTime;
    }

    public void setSortingDeltaTime(long sortingDeltaTime) {
        this.sortingDeltaTime = sortingDeltaTime;
    }


}
