package com.coresun.powerbank.data.network.entity;

/**
 * Created by Android on 2017/8/29.
 */

public class Params {
    private String name;
    private String value;

    public Params() {
    }
    public Params(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Params{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
