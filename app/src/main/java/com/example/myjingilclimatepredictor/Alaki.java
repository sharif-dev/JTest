package com.example.myjingilclimatepredictor;

public class Alaki {
    private String id;
    private String c;

//    Alaki(int id, char c) {
//        this.id = id;
//        this.c = c;
//    }

    String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "id= " + id +
                " , c= " + c +
                '}';
    }
}
