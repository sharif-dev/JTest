package com.example.myjingilclimatepredictor;

import java.util.ArrayList;

public class Map {
    private String type ;
    private Object query ;
    private ArrayList<City> features;
    private String attribution;

    class City{
        private String id;
        private String type;
        private Object place_type;
        private int relevance;
        private Object properties;
        private String text;
        private String place_name;
        private Object bbox;
        private ArrayList center;
        private Object geometry;
        private Object context;

        public ArrayList getCenter() {
            return center;
        }

        public String getPlace_name() {
            return place_name;
        }
    }

    public ArrayList<City> getFeatures() {
        return features;
    }
}
