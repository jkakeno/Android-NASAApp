package com.example.nasapp.Model.LibraryImage;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class LinksItem{

	@SerializedName("rel")
	private String rel;

	@SerializedName("href")
	private String href;

	@SerializedName("render")
	private String render;

	public void setRel(String rel){
		this.rel = rel;
	}

	public String getRel(){
		return rel;
	}

	public void setHref(String href){
		this.href = href;
	}

	public String getHref(){
		return href;
	}

	public void setRender(String render){
		this.render = render;
	}

	public String getRender(){
		return render;
	}

}