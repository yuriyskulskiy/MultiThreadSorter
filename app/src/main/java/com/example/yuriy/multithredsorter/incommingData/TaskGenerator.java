package com.example.yuriy.multithredsorter.incommingData;


import com.example.yuriy.multithredsorter.model.Mechanizm;

public interface TaskGenerator<T extends Mechanizm> {
    Mechanizm[][] getMultiTask();
}
