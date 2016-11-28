package com.loxboc.exampleapp.data.network;

import com.loxboc.exampleapp.data.model.Place;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

import static com.loxboc.exampleapp.di.module.NetworkModule.CONTENT_TYPE_JSON;

public interface FacebookApiService {
	@Headers(CONTENT_TYPE_JSON)
	@GET("/search")
	Observable<Place[]> getNearbyBusinesses(@Query("center") String location,
										  @Query("distance") int radiusInMeters,
										  @Query("type") String type,
										  @Query("q") String query,
										  @Query("fields") String fields);
}