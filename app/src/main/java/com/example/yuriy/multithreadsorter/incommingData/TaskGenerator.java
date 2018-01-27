package com.example.yuriy.multithreadsorter.incommingData;


import com.example.yuriy.multithreadsorter.model.Mechanizm;

public interface TaskGenerator<T extends Mechanizm> {
    Mechanizm[][] getMultiTask();
}
