package com.example.myjingilclimatepredictor;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GetMap extends Thread {
    final static String MAPBOX_TAG = "MAPBOX_TAG";

    private String query;
    private Context context;
    private List<Feature> cities;
    private ArrayList<String> string_city = new ArrayList<String>();
    private RequestQueue queue;
    private final String accessToken = "pk.eyJ1IjoibWFoc2lyYXQiLCJhIjoiY2s4NTR6bXBuMDI5YjNmc2p3dDh4NzM5YyJ9.kLU-vp3fVkOtvvBjZtGFaQ";
    private Handler handler;
    private ThumbnailAdapter adapter;

    public RequestQueue getQueue() {
        return queue;
    }

    static class Builder {
        String query;
        Context context;
        List<Feature>cities;
        Handler handler;
        ThumbnailAdapter adapter;

        Builder withQuery(String query) {
            this.query = query;
            return this;
        }

        Builder withContext(Context context) {
            this.context = context;
            return this;
        }

        Builder withCites(List<Feature> cities) {
            this.cities = cities;
            return this;
        }

        Builder withHandler(Handler handler){
            this.handler = handler;
            return this;
        }

         Builder withAdapter(ThumbnailAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
        GetMap build() {
            return new GetMap(query, context, cities, handler,adapter);
        }


    }

    private GetMap(String query, Context context, List<Feature> cities, Handler handler, ThumbnailAdapter adapter) {
        this.query = query;
        this.context = context;
        this.cities = cities;
        this.handler = handler;
        this.adapter = adapter;
    }

    @Override
    public void run() {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + query + ".json?access_token=" + accessToken;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(MAPBOX_TAG, "Get Response: "+response);
                        try {
                            Gson gson = new Gson();

                            final Map map = gson.fromJson(response, Map.class);
                            cities.clear();
                            for (Feature city : map.features
                            ) {
                                Log.d(MAPBOX_TAG, city.placeName);
                                Log.d(MAPBOX_TAG, "latitude: "+city.center.get(0).toString());
                                Log.d(MAPBOX_TAG, "longitude: "+city.center.get(1).toString());
                                cities.add(city);
                                string_city.add(city.placeName + "  " + city.center.get(0).toString() + "   " + city.center.get(1).toString());
                            }


                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

//                            Message message = new Message();
//                            Bundle messageBundle = new Bundle();
//                            messageBundle.putStringArrayList("CityMsg", string_city);
//                            message.setData(messageBundle);
//                            message.what = 1;
//                            handler.sendMessage(message);




//                            mainActivity.handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ListView listView = (ListView)mainActivity.findViewById(R.id.list_view);
//                                    listView.setAdapter(new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, string_city));
//                                    TextView textView = (TextView)mainActivity.findViewById(R.id.textView);
//                                    textView.setText(cities.get(0).placeName);
//                                }
//                            });

                        } catch (Exception e) {
//                            showError(R.string.mapbox_error);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    int code = error.networkResponse.statusCode;

//                    Message message = new Message();
//                    Bundle messageBundle = new Bundle();
//                    messageBundle.putString("ErrorMsg", new String(error.networkResponse.data,"UTF-8"));
//                    message.setData(messageBundle);
//                    message.what = 2;
//                    handler.sendMessage(message);
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
