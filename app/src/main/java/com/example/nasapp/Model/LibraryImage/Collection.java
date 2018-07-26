package com.example.nasapp.Model.LibraryImage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//@Generated("com.robohorse.robopojogenerator")
public class Collection{

	@SerializedName("metadata")
	private Metadata metadata;

	@SerializedName("items")
	private List<ItemsItem> items;

	public void setMetadata(Metadata metadata){
		this.metadata = metadata;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

}