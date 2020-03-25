package com.example.myjingilclimatepredictor;
//thanks to http://www.jsonschema2pojo.org/
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

class Context {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("short_code")
    @Expose
    public String shortCode;
    @SerializedName("wikidata")
    @Expose
    public String wikidata;
    @SerializedName("text")
    @Expose
    public String text;

}
class Feature {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("place_type")
    @Expose
    public List<String> placeType = null;
    @SerializedName("relevance")
    @Expose
    public int relevance;
    @SerializedName("properties")
    @Expose
    public Properties properties;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("place_name")
    @Expose
    public String placeName;
    @SerializedName("bbox")
    @Expose
    public List<Double> bbox = null;
    @SerializedName("center")
    @Expose
    public List<Double> center = null;
    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("context")
    @Expose
    public List<Context> context = null;
    @SerializedName("matching_text")
    @Expose
    public String matchingText;
    @SerializedName("matching_place_name")
    @Expose
    public String matchingPlaceName;

}
class Geometry {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("coordinates")
    @Expose
    public List<Double> coordinates = null;

}

public class Map {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("query")
    @Expose
    public List<String> query = null;
    @SerializedName("features")
    @Expose
    public List<Feature> features = null;
    @SerializedName("attribution")
    @Expose
    public String attribution;

}
class Properties {

    @SerializedName("wikidata")
    @Expose
    public String wikidata;
    @SerializedName("short_code")
    @Expose
    public String shortCode;
    @SerializedName("landmark")
    @Expose
    public boolean landmark;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("category")
    @Expose
    public String category;

}

