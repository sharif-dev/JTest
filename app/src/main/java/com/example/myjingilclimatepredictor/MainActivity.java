package com.example.myjingilclimatepredictor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class MainActivity<example> extends AppCompatActivity {

//    private static String example;
//    private static Alaki ex;
//
//    public static void write(String []args) {
//        try {
//            String str = ex.toString();
//            File newTextFile = new File("save.txt");
//
//            FileWriter fw = new FileWriter(newTextFile);
//            fw.write(str);
//            fw.close();
//
//        } catch (IOException iox) {
//
//            iox.printStackTrace();
//        }
//    }
//
//
//    final static String TAG = "SKY_TAG";
//    GetMap getMap;
//    GetSky getSky;
//
//    public MainActivity() throws FileNotFoundException {
//    }
    final static String TAG = "SKY_TAG";
    GetMap getMap;
    GetSky getSky;
    ArrayList<Feature> maplist;
    ArrayList<Datum__> skylist;
    List<String> fad = new ArrayList<>();
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                UpdateCityListView(msg);
            }
            else if (msg.what == 2 || msg.what == 3){
                ToastError(msg);
            }
            else {
                super.handleMessage(msg);
            }
        }
    };

    private void ToastError(Message msg) {
        Toast.makeText(MainActivity.this, msg.getData().getString("ErrorMsg"), Toast.LENGTH_LONG).show();
    }

    private void UpdateCityListView(Message msg) {
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, msg.getData().getStringArrayList("CityMsg")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skylist = new ArrayList<>();
        ConfigInputText();
//        searchForClimate("51.407", "35.7117", skylist);

    }

    private void ConfigInputText() {
        final EditText citysearch = (EditText)findViewById(R.id.city_name);
        Button searchbutton = (Button) findViewById(R.id.button5);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (citysearch.getText().toString().length() > 0){
                    maplist = new ArrayList<>();
                    searchForCity(citysearch.getText().toString(), maplist);
                }
            }
        });
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
        builder = builder.withHandler(handler);
        getSky = builder.build();
        getSky.start();

    }
    private void searchForCity(String searchedTerm, List<Feature>cities){
//        todo: Sbrineh : check for validity of searched term
        GetMap.Builder builder = new GetMap.Builder();
        builder = builder.withQuery(searchedTerm);
        builder = builder.withContext(getApplicationContext());
        builder = builder.withCites(cities);
        builder = builder.withHandler(handler);
        getMap = builder.build();
        getMap.start();

    }

}
