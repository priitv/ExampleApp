package com.loxboc.exampleapp.data.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Place {
	private String id;
	private String name;
	private LatLng location;
	private String imageUrl;
	@SerializedName("fan_count")
	private int likes;
}
