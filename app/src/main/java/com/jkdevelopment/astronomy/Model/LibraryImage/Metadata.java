package com.jkdevelopment.astronomy.Model.LibraryImage;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class Metadata{

	@SerializedName("total_hits")
	private int totalHits;

	public void setTotalHits(int totalHits){
		this.totalHits = totalHits;
	}

	public int getTotalHits(){
		return totalHits;
	}

}