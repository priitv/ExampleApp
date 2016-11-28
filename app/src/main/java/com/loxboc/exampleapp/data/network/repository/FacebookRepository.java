package com.loxboc.exampleapp.data.network.repository;

import com.google.android.gms.maps.model.LatLng;
import com.loxboc.exampleapp.data.model.Place;

import rx.Observable;

public interface FacebookRepository {
	Observable<Place> getNearbyPlaces(LatLng location);
}
