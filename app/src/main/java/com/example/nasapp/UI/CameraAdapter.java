package com.example.nasapp.UI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nasapp.Model.RoverList;
import com.example.nasapp.R;

import java.util.ArrayList;

/*Tutorial: https://www.androidtutorialpoint.com/material-design/android-custom-gridview-example-image-text/amp/
* Documentation: https://developer.android.com/guide/topics/ui/layout/gridview*/

public class CameraAdapter extends BaseAdapter {
    Context context;
    RoverList roverList;
//    String [] roverName;
    ArrayList<String> cameraList;
    private static LayoutInflater inflater=null;
    public int selectedCamera=-1;

    public CameraAdapter(Context context, RoverList roverList) {
        this.context = context;
        this.roverList=roverList;
    }

    public int getCount() {
        return roverList.getRoverList().get(0).getCameraList().size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        String camera = roverList.getRoverList().get(0).getCameraList().get(position);

        if (convertView == null) {
            roverGrid=new View(context);
            roverGrid=inflater.inflate(R.layout.camera_grid, null);
            TextView cameraName_tv = (TextView) roverGrid.findViewById(R.id.camera_name);
            cameraName_tv.setText(camera);
        } else {
            roverGrid = (View) convertView;
        }

        if(selectedCamera == position){
            roverGrid.setBackgroundColor(Color.RED);
        }else{
            roverGrid.setBackgroundColor(Color.TRANSPARENT);
        }

        return roverGrid;
    }
}