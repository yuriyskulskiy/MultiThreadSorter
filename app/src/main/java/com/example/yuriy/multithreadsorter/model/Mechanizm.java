package com.example.yuriy.multithreadsorter.model;


import java.io.Serializable;

import static com.example.yuriy.multithreadsorter.model.TypesConstants.UNDEFINED;

public abstract class Mechanizm implements Serializable {

    int id;
    String name;
    public static final String MECHANIZM_TYPE = UNDEFINED;

    @Override
    public String toString() {
        return "Mechanizm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMechanizmType() {
        return MECHANIZM_TYPE;
    }

}
