package com.example.myjingilclimatepredictor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    final static String TAG = "SKY_TAG";
    GetMap getMap;
    //    GetSky getSky;
    ArrayList<Feature> maplist;
    //    ArrayList<Datum__> skylist;
    ProgressBar progressBar;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            progressBar.setVisibility(View.GONE);
            if (msg.what == 1){
                UpdateCityListView(msg);
            }
            else if (msg.what == 2){
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedcity = (String) parent.getItemAtPosition(position);
                Intent nextactivity = new Intent(MainActivity.this, SecondActivity.class);
                nextactivity.putExtra("cityInfo", selectedcity);
                startActivity(nextactivity);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfigCitySearch();
//        searchForClimate("51.407", "35.7117", new ArrayList<Datum__>());
    }

    @Override
    protected void onStop () {
        super.onStop();
        try {
            getMap.getQueue().cancelAll(TAG);
        }catch (Exception e){
            Log.d(TAG, "no map open");

        }

    }

    private void ConfigCitySearch() {
        final EditText citysearch = (EditText)findViewById(R.id.city_name);
        Button searchbutton = (Button) findViewById(R.id.button5);
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (citysearch.getText().toString().length() > 0){
                    maplist = new ArrayList<>();
                    Toast.makeText(MainActivity.this, citysearch.getText().toString(), Toast.LENGTH_LONG).show();
                    searchForCity(citysearch.getText().toString(), maplist);
                }
            }
        });
    }


    private void searchForCity(String searchedTerm, List<Feature>cities){
        GetMap.Builder builder = new GetMap.Builder();
        builder = builder.withQuery(searchedTerm);
        builder = builder.withContext(getApplicationContext());
        builder = builder.withCites(cities);
        builder = builder.withHandler(handler);
        getMap = builder.build();
        getMap.start();

    }



}