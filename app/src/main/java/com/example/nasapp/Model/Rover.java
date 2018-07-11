package com.example.nasapp.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.nasapp.Model.RoverImage.RoverImages;

import java.util.ArrayList;

public class Rover implements Parcelable{
    String roverName;
    Uri roverImageUri;
    ArrayList<String> cameraList;
    String solSetting;
    String cameraSetting;
    RoverImages roverImages;

    public Rover(String roverName, Uri roverImageUri) {
        this.roverName = roverName;
        this.roverImageUri = roverImageUri;
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

    public ArrayList<String> getCameraList() {
        cameraList = new ArrayList<>();
        cameraList.add("Select a camera...");
        cameraList.add("Front Hazard Avoidance Camera");
        cameraList.add("Rear Hazard Avoidance Camera");
        cameraList.add("Mast Camera");
        cameraList.add("Chemistry and Camera Complex");
        cameraList.add("Mars Hand Lens Imager");
        cameraList.add("Mars Descent Imager");
        cameraList.add("Navigation Camera");
        cameraList.add("Panoramic Camera");
        cameraList.add("Thermal Emmision Spectrometer");
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

    public void setCameraSetting(String cameraSetting) {
        switch (cameraSetting){
            case "Front Hazard Avoidance Camera":
                this.cameraSetting = "fhaz";
                break;
            case "Rear Hazard Avoidance Camera":
                this.cameraSetting = "rhaz";
                break;
            case "Mast Camera":
                this.cameraSetting = "mast";
                break;
            case "Chemistry and Camera Complex":
                this.cameraSetting = "chemcam";
                break;
            case "Mars Hand Lens Imager":
                this.cameraSetting = "mahli";
                break;
            case "Mars Descent Imager":
                this.cameraSetting = "mardi";
                break;
            case "Navigation Camera":
                this.cameraSetting="navcam";
                break;
            case "Panoramic Camera":
                this.cameraSetting="pancam";
                break;
            case "Thermal Emmision Spectrometer":
                this.cameraSetting="minites";
                break;
        }
    }

    public RoverImages getRoverImages() {
        return roverImages;
    }

    public void setRoverImages(RoverImages roverImages) {
        this.roverImages = roverImages;
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
