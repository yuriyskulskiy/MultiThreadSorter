package com.example.yuriy.multithreadsorter.model;


import static com.example.yuriy.multithreadsorter.model.TypesConstants.PLANE;

public class Plane extends Mechanizm {


    public static final String MECHANIZM_TYPE = PLANE;

    public Plane() {
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
