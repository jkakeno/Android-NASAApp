package com.example.nasapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class Apod implements Parcelable{

	@SerializedName("date")
	private String date;

	@SerializedName("copyright")
	private String copyright;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("hdurl")
	private String hdurl;

	@SerializedName("service_version")
	private String serviceVersion;

	@SerializedName("explanation")
	private String explanation;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	public Apod(){}

	private Apod(Parcel in) {
		date = in.readString();
		copyright = in.readString();
		mediaType = in.readString();
		hdurl = in.readString();
		serviceVersion = in.readString();
		explanation = in.readString();
		title = in.readString();
		url = in.readString();
	}

	public static final Creator<Apod> CREATOR = new Creator<Apod>() {
		@Override
		public Apod createFromParcel(Parcel in) {
			return new Apod(in);
		}

		@Override
		public Apod[] newArray(int size) {
			return new Apod[size];
		}
	};

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setCopyright(String copyright){
		this.copyright = copyright;
	}

	public String getCopyright(){
		return copyright;
	}

	public void setMediaType(String mediaType){
		this.mediaType = mediaType;
	}

	public String getMediaType(){
		return mediaType;
	}

	public void setHdurl(String hdurl){
		this.hdurl = hdurl;
	}

	public String getHdurl(){
		return hdurl;
	}

	public void setServiceVersion(String serviceVersion){
		this.serviceVersion = serviceVersion;
	}

	public String getServiceVersion(){
		return serviceVersion;
	}

	public void setExplanation(String explanation){
		this.explanation = explanation;
	}

	public String getExplanation(){
		return explanation;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
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
		parcel.writeString(copyright);
		parcel.writeString(mediaType);
		parcel.writeString(hdurl);
		parcel.writeString(serviceVersion);
		parcel.writeString(explanation);
		parcel.writeString(title);
		parcel.writeString(url);
	}
}