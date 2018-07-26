package com.example.nasapp.Model.RoverImage;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class PhotosItem implements Parcelable{

	@SerializedName("sol")
	private int sol;

	@SerializedName("earth_date")
	private String earthDate;

	@SerializedName("id")
	private int id;

	@SerializedName("camera")
	private Camera camera;

	@SerializedName("rover")
	private Rover rover;

	@SerializedName("img_src")
	private String imgSrc;


	protected PhotosItem(Parcel in) {
		sol = in.readInt();
		earthDate = in.readString();
		id = in.readInt();
		imgSrc = in.readString();
	}

	public static final Creator<PhotosItem> CREATOR = new Creator<PhotosItem>() {
		@Override
		public PhotosItem createFromParcel(Parcel in) {
			return new PhotosItem(in);
		}

		@Override
		public PhotosItem[] newArray(int size) {
			return new PhotosItem[size];
		}
	};

	public void setSol(int sol){
		this.sol = sol;
	}

	public int getSol(){
		return sol;
	}

	public void setEarthDate(String earthDate){
		this.earthDate = earthDate;
	}

	public String getEarthDate(){
		return earthDate;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCamera(Camera camera){
		this.camera = camera;
	}

	public Camera getCamera(){
		return camera;
	}

	public void setRover(Rover rover){
		this.rover = rover;
	}

	public Rover getRover(){
		return rover;
	}

	public void setImgSrc(String imgSrc){
		this.imgSrc = imgSrc;
	}

	public String getImgSrc(){
		return imgSrc;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(sol);
		parcel.writeString(earthDate);
		parcel.writeInt(id);
		parcel.writeString(imgSrc);
	}
}