package com.example.yuriy.multithreadsorter.model;

import static com.example.yuriy.multithreadsorter.model.TypesConstants.CAR;


public class Car extends Mechanizm {

    public static final String MECHANIZM_TYPE = CAR;

    public Car() {
    }

    @Override
    public String toString() {
        return MECHANIZM_TYPE + '{' +
                super.toString() +
                '}';
    }

    public String getMechanizmType() {
        return MECHANIZM_TYPE;
    }
}
