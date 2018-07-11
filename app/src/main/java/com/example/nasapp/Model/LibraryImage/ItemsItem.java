package com.example.nasapp.Model.LibraryImage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//@Generated("com.robohorse.robopojogenerator")
public class ItemsItem{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("links")
	private List<LinksItem> links;

	@SerializedName("href")
	private String href;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setLinks(List<LinksItem> links){
		this.links = links;
	}

	public List<LinksItem> getLinks(){
		return links;
	}

	public void setHref(String href){
		this.href = href;
	}

	public String getHref(){
		return href;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"data = '" + data + '\'' + 
			",links = '" + links + '\'' + 
			",href = '" + href + '\'' + 
			"}";
		}
}