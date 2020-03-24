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
import com.mapbox.mapboxsdk.Mapbox;
//import com.mapbox.mapboxsdk.maps.MapView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MAPBOX_TAG";
    GetMap getMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        search for tehran:
        searchForCity("tehran",new ArrayList<String>(), new ArrayList<ArrayList<Double>>());



    }
    @Override
    protected void onStop () {
        super.onStop();
        try {
            getMap.getQueue().cancelAll(TAG);
        }catch (Exception e){
            Log.d(TAG, "no map");

        }
    }


    private void searchForCity(String searchedTerm, ArrayList<String> cityNames, ArrayList<ArrayList<Double>> cityCenters){
//        todo: Sbrineh : check for validity of searched term
        GetMap.Builder builder = new GetMap.Builder();
        builder = builder.withQuery(searchedTerm);
        builder = builder.withContext(getApplicationContext());
        builder = builder.withCityNames(cityNames);
        builder = builder.withCenterClasses(cityCenters);
        getMap = builder.build();
        getMap.start();

    }
}
