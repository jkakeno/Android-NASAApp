package com.jkdevelopment.astronomy.Model.Earth;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class ResultsItem{

	@SerializedName("date")
	private String date;

	@SerializedName("id")
	private String id;

	/*Added this field to encapsulate image with in result so that is can be used with concatmap operator of result observable.*/
	Image image;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}