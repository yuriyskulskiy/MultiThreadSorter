package com.example.yuriy.multithreadsorter.sortUtils;

import com.example.yuriy.multithreadsorter.model.Mechanizm;


public abstract class ComparableAbstractSort extends BaseSortedClass<Mechanizm> {

    protected boolean lexicographyCompare(Mechanizm first, Mechanizm second) {
        return first.getName().compareTo(second.getName()) > 0;
    }

}
