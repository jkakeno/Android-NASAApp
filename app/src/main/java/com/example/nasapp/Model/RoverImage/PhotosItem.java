package com.example.nasapp.Model.RoverImage;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

//@Generated("com.robohorse.robopojogenerator")
public class PhotosItem implements Parcelable{

	@SerializedName("earth_date")
	private String earthDate;

	@SerializedName("img_src")
	private String imgSrc;

	ArrayList<Annotation> annotationList;

	protected PhotosItem(Parcel in) {
		earthDate = in.readString();
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

	public void setEarthDate(String earthDate){
		this.earthDate = earthDate;
	}

	public String getEarthDate(){
		return earthDate;
	}

	public void setImgSrc(String imgSrc){
		this.imgSrc = imgSrc;
	}

	public String getImgSrc(){
		return imgSrc;
	}

	public ArrayList<Annotation> getAnnotationList() {
		return annotationList;
	}

	public void setAnnotationList(ArrayList<Annotation> annotationList) {
		this.annotationList = annotationList;
	}

	@Override
 	public String toString(){
		return 
			"PhotosItem{" + 
			"earth_date = '" + earthDate + '\'' + 
			",img_src = '" + imgSrc + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(earthDate);
		parcel.writeString(imgSrc);
	}
}