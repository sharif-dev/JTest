package com.example.myjingilclimatepredictor;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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


    final static String TAG = "SKY_TAG";
    GetMap getMap;
    GetSky getSky;

    public MainActivity() throws FileNotFoundException {
    }
    Alaki tmp = new Alaki();
    int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchForCity("tehran", new ArrayList<Feature>());
        searchForClimate("37.8267", "-122.4233", new ArrayList<Datum__>());
        Alaki tmp = new Alaki();

        if (counter == 1) {
            tmp.setId("123");
            tmp.setC("ali");
        } else {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader("save.txt"));
                String str = reader.readLine();
                String[] array = str.split(" ");
                tmp.setId(array[1]);
                tmp.setC(array[3]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onStop () {
        super.onStop();
        counter += 1;

        String ls = "id " + tmp.getId() + "c " + tmp.getC();
        try {
            FileWriter fw = new FileWriter("save.txt");
            fw.write(ls);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
