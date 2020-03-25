package com.example.myjingilclimatepredictor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.mapbox.mapboxsdk.maps.MapView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "SKY_TAG";
    GetMap getMap;
    GetSky getSky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchForCity("tehran", new ArrayList<Feature>());
        searchForClimate("37.8267", "-122.4233", new ArrayList<Datum__>());



    }
    @Override
    protected void onStop () {
        super.onStop();
        try {
            getMap.getQueue().cancelAll(TAG);
        }catch (Exception e){
            Log.d(TAG, "no map open");

        }
        try {
            getSky.getQueue().cancelAll(TAG);
        }catch (Exception e){
            Log.d(TAG, "no sky open");

        }
    }




    private void searchForClimate(String latitude, String longitude, ArrayList<Datum__> days){
//        todo Sabrineh check for validity of inputs
        GetSky.Builder builder = new GetSky.Builder();
        builder = builder.withLatitudeAndLongitude(latitude, longitude);
        builder = builder.withContext(getApplicationContext());
        builder = builder.withDays(days);
        getSky = builder.build();
        getSky.start();

    }
    private void searchForCity(String searchedTerm, List<Feature>cities){
//        todo: Sbrineh : check for validity of searched term
        GetMap.Builder builder = new GetMap.Builder();
        builder = builder.withQuery(searchedTerm);
        builder = builder.withContext(getApplicationContext());
        builder = builder.withCites(cities);
        getMap = builder.build();
        getMap.start();

    }
}
