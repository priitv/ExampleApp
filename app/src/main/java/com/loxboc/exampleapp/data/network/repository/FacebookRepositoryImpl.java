package com.loxboc.exampleapp.data.network.repository;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.loxboc.exampleapp.data.model.Place;
import com.loxboc.exampleapp.data.network.FacebookApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static com.loxboc.exampleapp.UtilTools.getLatLngQuery;

@Singleton
public class FacebookRepositoryImpl implements FacebookRepository {
	private FacebookApiService facebookApiService;

	@Inject
	public FacebookRepositoryImpl(FacebookApiService facebookApiService) {
		this.facebookApiService = facebookApiService;
	}

	@NonNull
	@Override
	@CheckResult
	public Observable<Place> getNearbyPlaces(LatLng location) {
		return facebookApiService.getNearbyBusinesses(
					getLatLngQuery(location), 10000, "place",
					null /* "restaurant,bar" */, "id,name,location,fan_count,category,picture")
				.flatMap(Observable::from);
	}
}
