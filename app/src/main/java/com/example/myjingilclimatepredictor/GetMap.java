package com.example.myjingilclimatepredictor;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class GetMap extends Thread {
    final static String MAPBOX_TAG = "MAPBOX_TAG";

    private String query;
    private Context context;
    private final ArrayList<String> cityNames;
    private ArrayList<ArrayList<Double>> centerClasses;
    private RequestQueue queue;
    private final String accessToken = "pk.eyJ1IjoibWFoc2lyYXQiLCJhIjoiY2s4NTR6bXBuMDI5YjNmc2p3dDh4NzM5YyJ9.kLU-vp3fVkOtvvBjZtGFaQ";

    public RequestQueue getQueue() {
        return queue;
    }

    static class Builder {
        String query;
        Context context;
        ArrayList<String> cityNames;
        ArrayList<ArrayList<Double>> centerClasses = new ArrayList<>();

        Builder withQuery(String query) {
            this.query = query;
            return this;
        }

        Builder withContext(Context context) {
            this.context = context;
            return this;
        }

        Builder withCityNames(ArrayList<String> cityNames) {
            this.cityNames = cityNames;
            return this;
        }


        Builder withCenterClasses(ArrayList<ArrayList<Double>> centerClasses) {
            this.centerClasses = centerClasses;
            return this;
        }

        GetMap build() {
            return new GetMap(query, context, cityNames, centerClasses);
        }
    }

    private GetMap(String query, Context context, ArrayList<String> cityNames, ArrayList<ArrayList<Double>> centerClasses) {
        this.query = query;
        this.context = context;
        this.cityNames = cityNames;
        this.centerClasses = centerClasses;
    }

    @Override
    public void run() {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + query + ".json?access_token=" + accessToken;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(MAPBOX_TAG, "Get Response");
                        try {
                            Gson gson = new Gson();

                            Map map = gson.fromJson(response, Map.class);
                            centerClasses.clear();
                            cityNames.clear(); // clear the last list
                            for (Map.City city : map.getFeatures()
                            ) {
                                Log.d(MAPBOX_TAG, city.getPlace_name());

                                cityNames.add(city.getPlace_name());
                                centerClasses.add(city.getCenter());
                            }

//                            todo::Sabrineh - send to UI

                        } catch (Exception e) {
//                            showError(R.string.mapbox_error);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    int code = error.networkResponse.statusCode;
//                    todo Sabrineh decide on proper error message for following senarios
                    switch (code) {
                        case 401:
//                token error
                            break;
                        case 403:
//                forbidden
                            break;
                        case 404:
//                not found
                            break;
                        case 422:
//                query length
                            break;
                        case 429:
//                rate
                            break;
                        default:
//                            in this case you have to send to handler
                            break;
                    }
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    Log.d(MAPBOX_TAG, "error is: " + responseBody);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.d(MAPBOX_TAG, error.toString());

                }

            }
        });

        queue.add(stringRequest);
    }




}
