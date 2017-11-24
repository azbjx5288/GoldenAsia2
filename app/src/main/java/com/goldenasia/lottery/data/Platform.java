package com.goldenasia.lottery.data;

/**
 * Created by ACE-PC on 2017/1/27.
 */

public class Platform {
    private int id;
    private String name;
    private String cnname;

    public Platform(int id, String name, String cnname) {
        this.id = id;
        this.name = name;
        this.cnname = cnname;
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

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }
}
