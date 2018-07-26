package com.example.nasapp.Model.LibraryImage;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class LibraryImageCollection implements Parcelable{

	@SerializedName("collection")
	private Collection collection;

	public LibraryImageCollection() {
	}

	protected LibraryImageCollection(Parcel in) {
	}

	public static final Creator<LibraryImageCollection> CREATOR = new Creator<LibraryImageCollection>() {
		@Override
		public LibraryImageCollection createFromParcel(Parcel in) {
			return new LibraryImageCollection(in);
		}

		@Override
		public LibraryImageCollection[] newArray(int size) {
			return new LibraryImageCollection[size];
		}
	};

	public void setCollection(Collection collection){
		this.collection = collection;
	}

	public Collection getCollection(){
		return collection;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
	}
}