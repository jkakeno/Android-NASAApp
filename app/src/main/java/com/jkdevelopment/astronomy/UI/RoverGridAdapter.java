package com.jkdevelopment.astronomy.UI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkdevelopment.astronomy.Model.Rover;
import com.jkdevelopment.astronomy.Model.RoverList;
import com.jkdevelopment.astronomy.R;


public class RoverGridAdapter extends BaseAdapter {

    private static final String TAG = RoverGridAdapter.class.getSimpleName();
    Context context;
    RoverList roverList;
    private static LayoutInflater inflater=null;
    public int selectedRover=-1;

    public RoverGridAdapter(Context context, RoverList roverList) {
        this.context = context;
        this.roverList=roverList;
    }

    public int getCount() {
        return roverList.getRoverList().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View roverGrid;
        TextView roverName_tv;
        ImageView roverImage_iv;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Rover rover = roverList.getRoverList().get(position);

        if (convertView == null) {
            roverGrid=new View(context);
            roverGrid=inflater.inflate(R.layout.rover_grid, null);
            roverName_tv = (TextView) roverGrid.findViewById(R.id.rover_name);
            roverImage_iv = (ImageView) roverGrid.findViewById(R.id.rover_image);
            roverName_tv.setText(rover.getRoverName());
            roverImage_iv.setImageURI(rover.getRoverImageUri());
        } else {
            roverGrid = (View) convertView;
        }

        if(selectedRover == position){
            roverGrid.setBackgroundColor(Color.RED);
        }else{
            roverGrid.setBackgroundColor(Color.TRANSPARENT);
        }

        return roverGrid;
    }
}