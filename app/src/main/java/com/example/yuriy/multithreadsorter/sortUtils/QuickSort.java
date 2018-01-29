package com.example.yuriy.multithreadsorter.sortUtils;


import android.util.Log;

import com.example.yuriy.multithreadsorter.model.Mechanizm;

public class QuickSort extends ComparableAbstractSort {


    @Override
    public Mechanizm[] sort(Mechanizm[] dataToSort) {
        if (dataToSort == null) {
            return null;
        }
        if (dataToSort.length <= 1) {
            return dataToSort;
        }

        return doQuickSort(dataToSort);
    }

    private Mechanizm[] doQuickSort(Mechanizm[] dataToSort) {
        printData(dataToSort);
        quicksort(dataToSort, 0, dataToSort.length - 1);
        return dataToSort;
    }

    private void printData(Mechanizm[] mList) {
        for (Mechanizm m :
                mList) {
            Log.w("Sort", m.getName() + "\n");
        }
    }

    private void quicksort(Mechanizm[] array1, int startIndex, int endIndex) {
        Mechanizm pivotValueObject = getPivot(array1, startIndex, endIndex);
        int currentStartIndex = startIndex;
        int currentEndIndex = endIndex;

        while (currentStartIndex < currentEndIndex) {
            while (lexicographyCompare(pivotValueObject, array1[currentStartIndex])) {
                currentStartIndex++;
            }

            while (lexicographyCompare(array1[currentEndIndex], pivotValueObject)) {
                currentEndIndex--;
            }
            if (currentStartIndex < currentEndIndex) {
                Mechanizm buffer = array1[currentStartIndex];
                array1[currentStartIndex] = array1[currentEndIndex];
                array1[currentEndIndex] = buffer;
                currentStartIndex++;
                currentEndIndex--;
            }
        }
        if (startIndex < currentStartIndex) {
            quicksort(array1, startIndex, currentStartIndex - 1);
        }
        if (currentEndIndex < endIndex) {

            quicksort(array1, currentStartIndex, endIndex);
        }

    }


    private static Mechanizm getPivot(Mechanizm[] array1, int startIndex, int endIndex) {
        return array1[endIndex];
    }

}
