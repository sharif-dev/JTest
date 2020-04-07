package com.example.myjingilclimatepredictor;

public class Alaki {

    char name;
    int id;

    public Alaki(char name, int id) {
        name = name;
        id = id;
    }

    @Override
    public String toString() {
        return "Alaki{" +
                "name=" + name +
                ", id=" + id +
                '}';
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
