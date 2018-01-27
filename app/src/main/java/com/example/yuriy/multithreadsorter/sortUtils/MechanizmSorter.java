package com.example.yuriy.multithreadsorter.sortUtils;



import com.example.yuriy.multithreadsorter.model.Mechanizm;


public class MechanizmSorter extends BaseSortedClass<Mechanizm[]> {


    @Override
    public Mechanizm[] sort(Mechanizm[] dataToSort) {
        if (dataToSort == null) {
            return null;
        }
        if (dataToSort.length <= 1) {
            return dataToSort;
        }
        return doBubbleSort(dataToSort);
    }

    private Mechanizm[] doBubbleSort(Mechanizm[] dataToSort) {

        int n = dataToSort.length;
        Mechanizm tempHolder = null;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (lexicographyCompare(dataToSort[j - 1], dataToSort[j])) {
                    tempHolder = dataToSort[j - 1];
                    dataToSort[j - 1] = dataToSort[j];
                    dataToSort[j] = tempHolder;
                }
            }

        }
        return dataToSort;
    }

    private boolean lexicographyCompare(Mechanizm first, Mechanizm second) {
        return first.getName().compareTo(second.getName()) > 0;
    }


}