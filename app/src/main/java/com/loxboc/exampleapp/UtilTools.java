package com.loxboc.exampleapp;

import com.google.android.gms.maps.model.LatLng;

public class UtilTools {
	public static String getLatLngQuery(LatLng latLng) {
		return latLng.latitude + "," + latLng.longitude;
	}
}
