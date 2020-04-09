package com.example.myjingilclimatepredictor;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    final static String TAG = "CLIMATE_TAG";
    GetSky getSky;
    ArrayList<Datum__> skylist;
    ProgressBar progressBar;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            progressBar.setVisibility(View.GONE);
            if (msg.what == 1){
                UpdateCityWeather(msg);
            }
            else if (msg.what == 2){
                ToastError(msg);
            }
        }
    };

    private void ToastError(Message msg) {
        Toast.makeText(SecondActivity.this, msg.getData().getString("ErrorMsg"), Toast.LENGTH_LONG).show();
    }


    public String felan(String a){

        if (a.contains("cloudy")){
            return "@android:mipmap/cloudy.jpg";
        } else if (a.contains("haily")){
            return "@android:mipmap/haily.jpg";
        } else if (a.contains("heavy_rainy")) {
            return "@android:mipmap/heavy_rainy.jpg";
        }  else if (a.contains("light_cloudy")) {
            return "@android:mipmap/light_cloudy.jpg";
        }  else if (a.contains("lightning")) {
            return "@android:mipmap/lightning.jpg";
        }  else if (a.contains("not_heavy_raining")) {
            return "@android:mipmap/not_heavy_raining.jpg";
        }  else if (a.contains("rainy")) {
            return "@android:mipmap/rainy.jpg";
        }  else if (a.contains("snowy")) {
            return "@android:mipmap/snowy.jpg";
        }  else if (a.contains("sunny")) {
            return "@android:mipmap/sunny.jpg";
        }
    }

    public void UpdateCityWeather(Message msg){
        TextView view0 = (TextView)findViewById(R.id.textView2);
        view0.setText(msg.getData().getStringArrayList("cityinfo").get(0));
        felan(msg.getData().getStringArrayList("cityinfo").get(0));

        TextView view1 = (TextView)findViewById(R.id.textView);
        view1.setText(msg.getData().getStringArrayList("cityinfo").get(1));
        felan(msg.getData().getStringArrayList("cityinfo").get(1));

        TextView view2 = (TextView)findViewById(R.id.textView3);
        view2.setText(msg.getData().getStringArrayList("cityinfo").get(2));
        felan(msg.getData().getStringArrayList("cityinfo").get(2));

        TextView view3 = (TextView)findViewById(R.id.textView5);
        view3.setText(msg.getData().getStringArrayList("cityinfo").get(3));
        felan(msg.getData().getStringArrayList("cityinfo").get(3));

        TextView view4 = (TextView)findViewById(R.id.textView6);
        view4.setText(msg.getData().getStringArrayList("cityinfo").get(4));
        felan(msg.getData().getStringArrayList("cityinfo").get(4));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        GetPassedMessage(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            getSky.getQueue().cancelAll(TAG);
        }catch (Exception e){
            Log.d(TAG, "no sky open");

        }
    }

    private void GetPassedMessage(Intent intent) {
        String selectedcity = intent.getStringExtra("cityInfo");
        String[] citydetail = selectedcity.split("  ");
        String latitude = citydetail[citydetail.length - 2];
        String longitude = citydetail[citydetail.length - 1];
        Log.i("CITY_SELECTED", latitude + longitude);
        skylist = new ArrayList<>();
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
//        progressBar.setVisibility(View.VISIBLE);
        searchForClimate(latitude, longitude, skylist);
    }

    private void searchForClimate(String latitude, String longitude, ArrayList<Datum__> days){
        GetSky.Builder builder = new GetSky.Builder();
        builder = builder.withLatitudeAndLongitude(latitude, longitude);
        builder = builder.withContext(SecondActivity.this);
        builder = builder.withDays(days);
        builder = builder.withHandler(handler);
        getSky = builder.build();
        getSky.start();
    }
}
