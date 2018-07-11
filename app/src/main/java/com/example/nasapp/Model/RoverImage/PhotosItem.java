package com.example.nasapp.Model.RoverImage;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class PhotosItem{

	@SerializedName("earth_date")
	private String earthDate;

	@SerializedName("img_src")
	private String imgSrc;

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

	@Override
 	public String toString(){
		return 
			"PhotosItem{" + 
			"earth_date = '" + earthDate + '\'' + 
			",img_src = '" + imgSrc + '\'' + 
			"}";
		}
}