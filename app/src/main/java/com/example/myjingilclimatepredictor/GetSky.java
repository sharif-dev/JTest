package com.example.myjingilclimatepredictor;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class GetSky extends Thread {
    final static String SKY_TAG = "SKY_TAG";

    private String latitude;
    private String longitude;
    private Context context;
    private ArrayList<Datum__> days;
    private ArrayList<String> cityinfo = new ArrayList<String>();
    private RequestQueue queue;
    private final String secretKey = "3d056b7d74d0cad0d331a596046a868f";
    private Handler handler;


    public RequestQueue getQueue() {
        return queue;
    }

    static class Builder {
        String latitude;
        String longitude;

        Context context;
        ArrayList<Datum__> days = new ArrayList<>();
        Handler handler;

        GetSky.Builder withLatitudeAndLongitude(String latitude, String longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            return this;
        }

        GetSky.Builder withContext(Context context) {
            this.context = context;
            return this;
        }

        GetSky.Builder withDays(ArrayList<Datum__> days) {
            this.days = days;
            return this;
        }

        GetSky.Builder withHandler(Handler handler){
            this.handler = handler;
            return this;
        }


        GetSky build() {
            return new GetSky(latitude, longitude, context, days, handler);
        }
    }

    private GetSky(String latitude, String longitude, Context context, ArrayList<Datum__> days, Handler handler) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.context = context;
        this.days = days;
        this.handler = handler;
    }

    @Override
    public void run() {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.darksky.net/forecast/"+secretKey+"/"+latitude+","+longitude;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(SKY_TAG, "Get Response");
                        try {
                            Gson gson = new Gson();

                            Sky sky = gson.fromJson(response, Sky.class);
                            days.clear();
                            for (Datum__ day: sky.daily.data
                            ) {
                                Log.d(SKY_TAG, day.summary);
                                days.add(day);
                                cityinfo.add(day.summary);

                            }

                            Message message = new Message();
                            Bundle messageBundle = new Bundle();
                            messageBundle.putStringArrayList("cityinfo", cityinfo);
                            message.setData(messageBundle);
                            message.what = 1;
                            handler.sendMessage(message);

                        } catch (Exception e) {
//                            showError(R.string.mapbox_error);
                            Log.d(SKY_TAG, e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    int code = error.networkResponse.statusCode;

                    Message message = new Message();
                    Bundle messageBundle = new Bundle();
                    messageBundle.putString("ErrorMsg", new String(error.networkResponse.data,"UTF-8"));
                    message.setData(messageBundle);
                    message.what = 2;
                    handler.sendMessage(message);
                    String responseBody = new String(error.networkResponse.data, "utf-8");

                    Log.d(SKY_TAG, "error is: " + responseBody);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.d(SKY_TAG, error.toString());

                }

            }
        });

        queue.add(stringRequest);
    }


}
