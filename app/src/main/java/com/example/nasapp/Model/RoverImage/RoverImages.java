package com.example.nasapp.Model.RoverImage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//@Generated("com.robohorse.robopojogenerator")
public class RoverImages{

	@SerializedName("photos")
	private List<PhotosItem> photos;

	public void setPhotos(List<PhotosItem> photos){
		this.photos = photos;
	}

	public List<PhotosItem> getPhotos(){
		return photos;
	}

	@Override
 	public String toString(){
		return 
			"RoverImages{" + 
			"photos = '" + photos + '\'' + 
			"}";
		}
}