package com.jkdevelopment.astronomy.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.jkdevelopment.astronomy.Model.RoverImage.RoverImages;

import java.util.ArrayList;

public class Rover implements Parcelable{
    String roverName;
    Uri roverImageUri;
    ArrayList<String> cameraList;
    String solSetting;
    String cameraSetting;
    RoverImages roverImages;
    boolean selected;

    public Rover(String roverName, Uri roverImageUri) {
        this.roverName = roverName;
        this.roverImageUri = roverImageUri;
    }

    public Rover(){
    }

    protected Rover(Parcel in) {
        roverName = in.readString();
        roverImageUri = in.readParcelable(Uri.class.getClassLoader());
        cameraList = in.createStringArrayList();
        solSetting = in.readString();
        cameraSetting = in.readString();
    }

    public static final Creator<Rover> CREATOR = new Creator<Rover>() {
        @Override
        public Rover createFromParcel(Parcel in) {
            return new Rover(in);
        }

        @Override
        public Rover[] newArray(int size) {
            return new Rover[size];
        }
    };

    public String getRoverName() {
        return roverName;
    }

    public void setRoverName(String roverName) {
        this.roverName = roverName;
    }

    public Uri getRoverImageUri() {
        return roverImageUri;
    }

    public void setRoverImageUri(Uri roverImageUri) {
        this.roverImageUri = roverImageUri;
    }

    public ArrayList<String> getCameraList(){
        cameraList = new ArrayList<>();
        cameraList.add("All");
        cameraList.add("fhaz");
        cameraList.add("rhaz");
        cameraList.add("mast");
        cameraList.add("chemcam");
        cameraList.add("mahli");
        cameraList.add("mardi");
        cameraList.add("navcam");
        cameraList.add("pancam");
        cameraList.add("minites");
        return cameraList;
    }

    public void setCameraList(ArrayList<String> cameraList) {
        this.cameraList = cameraList;
    }

    public String getSolSetting() {
        return solSetting;
    }

    public void setSolSetting(String solSetting) {
        this.solSetting = solSetting;
    }

    public String getCameraSetting() {
        return cameraSetting;
    }

    public void setCameraSetting(String cameraSetting){
        this.cameraSetting = cameraSetting;
    }

    public RoverImages getRoverImages() {
        return roverImages;
    }

    public void setRoverImages(RoverImages roverImages) {
        this.roverImages = roverImages;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(roverName);
        parcel.writeParcelable(roverImageUri, i);
        parcel.writeStringList(cameraList);
        parcel.writeString(solSetting);
        parcel.writeString(cameraSetting);
    }
}
