package com.example.nasapp.Model.Earth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class Image implements Parcelable{

	@SerializedName("date")
	private String date;

	@SerializedName("cloud_score")
	private double cloudScore;

	@SerializedName("url")
	private String url;

	protected Image(Parcel in) {
		date = in.readString();
		cloudScore = in.readDouble();
		url = in.readString();
	}

	public static final Creator<Image> CREATOR = new Creator<Image>() {
		@Override
		public Image createFromParcel(Parcel in) {
			return new Image(in);
		}

		@Override
		public Image[] newArray(int size) {
			return new Image[size];
		}
	};

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setCloudScore(double cloudScore){
		this.cloudScore = cloudScore;
	}

	public double getCloudScore(){
		return cloudScore;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(date);
		parcel.writeDouble(cloudScore);
		parcel.writeString(url);
	}
}