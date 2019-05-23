package com.jkdevelopment.astronomy.Model.RoverImage;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class CamerasItem{

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("name")
	private String name;

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
}