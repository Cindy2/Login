package com.example.admin.login.domain;

/**
 * Created by Admin on 2017/2/16.
 */

public class weather {
    public String getName() {
        return name;
    }

    public weather setName(String name) {
        this.name = name;
        return this;
    }

    public String getTemp() {
        return temp;
    }

    public weather setTemp(String temp) {
        this.temp = temp;
        return this;
    }

    public String getPm() {
        return pm;
    }

    public weather setPm(String pm) {
        this.pm = pm;
        return this;
    }

    @Override
    public String toString() {
        return "weather{" +
                "name='" + name + '\'' +
                ", temp='" + temp + '\'' +
                ", pm='" + pm + '\'' +
                '}';
    }

    private String name;
    private String temp;
    private String pm;

}
