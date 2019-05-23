package com.jkdevelopment.astronomy.Model.RoverImage;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class Camera{

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("rover_id")
	private int roverId;

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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setRoverId(int roverId){
		this.roverId = roverId;
	}

	public int getRoverId(){
		return roverId;
	}
}