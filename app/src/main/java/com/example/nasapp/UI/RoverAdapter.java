package com.example.nasapp.UI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasapp.Model.Rover;
import com.example.nasapp.Model.RoverList;
import com.example.nasapp.R;

/*Tutorial: https://www.learn2crack.com/2014/01/android-custom-gridview.html
* Documentation: https://developer.android.com/guide/topics/ui/layout/gridview*/

public class RoverAdapter extends BaseAdapter {

    private static final String TAG = RoverAdapter.class.getSimpleName();
    Context context;
    RoverList roverList;
    private static LayoutInflater inflater=null;
    public int selectedRover=-1;

    public RoverAdapter(Context context, RoverList roverList) {
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

    //    Context context;
//    String [] roverName;
//    int[] roverImageId;
//    private static LayoutInflater inflater=null;
//
//    public RoverAdapter(Context context, String[] roverTitle, int[] roverImageId) {
//        this.context = context;
//        this.roverName =roverTitle;
//        this.roverImageId=roverImageId;
//    }
//
//    public int getCount() {
//        return roverName.length;
//    }
//
//    public Object getItem(int position) {
//        return null;
//    }
//
//    public long getItemId(int position) {
//        return 0;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View roverGrid;
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        if (convertView == null) {
//            roverGrid=new View(context);
//            roverGrid=inflater.inflate(R.layout.rover_grid, null);
//            TextView roverName_tv = (TextView) roverGrid.findViewById(R.id.rover_name);
//            ImageView roverImage_iv = (ImageView) roverGrid.findViewById(R.id.rover_image);
//            roverName_tv.setText(roverName[position]);
//            roverImage_iv.setImageResource(roverImageId[position]);
//        } else {
//            roverGrid = (View) convertView;
//        }
//
//        return roverGrid;
//    }
}