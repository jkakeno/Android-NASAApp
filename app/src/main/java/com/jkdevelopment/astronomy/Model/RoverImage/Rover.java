package com.jkdevelopment.astronomy.Model.RoverImage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//@Generated("com.robohorse.robopojogenerator")
public class Rover{

	@SerializedName("max_sol")
	private int maxSol;

	@SerializedName("cameras")
	private List<CamerasItem> cameras;

	@SerializedName("max_date")
	private String maxDate;

	@SerializedName("total_photos")
	private int totalPhotos;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("launch_date")
	private String launchDate;

	@SerializedName("landing_date")
	private String landingDate;

	@SerializedName("status")
	private String status;

	public void setMaxSol(int maxSol){
		this.maxSol = maxSol;
	}

	public int getMaxSol(){
		return maxSol;
	}

	public void setCameras(List<CamerasItem> cameras){
		this.cameras = cameras;
	}

	public List<CamerasItem> getCameras(){
		return cameras;
	}

	public void setMaxDate(String maxDate){
		this.maxDate = maxDate;
	}

	public String getMaxDate(){
		return maxDate;
	}

	public void setTotalPhotos(int totalPhotos){
		this.totalPhotos = totalPhotos;
	}

	public int getTotalPhotos(){
		return totalPhotos;
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

	public void setLaunchDate(String launchDate){
		this.launchDate = launchDate;
	}

	public String getLaunchDate(){
		return launchDate;
	}

	public void setLandingDate(String landingDate){
		this.landingDate = landingDate;
	}

	public String getLandingDate(){
		return landingDate;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}