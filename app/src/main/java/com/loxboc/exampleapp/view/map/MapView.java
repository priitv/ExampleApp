package com.loxboc.exampleapp.view.map;

import com.google.android.gms.maps.model.LatLng;
import com.loxboc.exampleapp.data.model.Place;
import com.loxboc.exampleapp.view.ContentViewContract;

import java.util.List;

public interface MapView extends ContentViewContract {
	void drawPolyline(List<LatLng> points);

	void animateCameraTo(LatLng a, LatLng b);

	void setLoadingNearbyPlaces(boolean loading);

	void addPlaceMarker(Place place);
}
