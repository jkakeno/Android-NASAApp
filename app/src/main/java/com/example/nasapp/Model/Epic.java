package com.example.nasapp.Model;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class Epic{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public String getImageUrl(){
		String[] dateParts = this.date.split("-");
		String year = dateParts[0];
		String month = dateParts[1];
		String dayString = dateParts[2];
		String[] dayParts = dayString.split(" ");
		String day = dayParts[0];

		return "https://epic.gsfc.nasa.gov/archive/natural/" + year + "/" + month + "/" + day + "/png/" + this.image + ".png";
	}

	@Override
 	public String toString(){
		return 
			"Epic{" + 
			"date = '" + date + '\'' + 
			",image = '" + image + '\'' + 
			"}";
		}
}