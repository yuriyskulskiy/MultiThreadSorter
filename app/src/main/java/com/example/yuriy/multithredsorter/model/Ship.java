package com.example.yuriy.multithredsorter.model;


import static com.example.yuriy.multithredsorter.model.TypesConstants.SHIP;

public class Ship extends Mechanizm {


    public static final String MECHANIZM_TYPE = SHIP;

    public Ship() {
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
