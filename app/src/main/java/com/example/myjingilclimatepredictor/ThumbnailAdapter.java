package com.example.myjingilclimatepredictor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ThumbnailAdapter extends ArrayAdapter<Feature> {

    ArrayList<Feature> arr;
    public ThumbnailAdapter(@NonNull Context context, int resource, ArrayList<Feature> objects) {
        super(context, resource, objects);
        this.arr=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_row, parent,false);
        }

        TextView tvCity = (TextView) view.findViewById(R.id.tvCity);

//        return super.getView(position, convertView, parent);
        tvCity.setText(arr.get(position).placeName);
        return view;
    }
}
