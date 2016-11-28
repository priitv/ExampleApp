package com.loxboc.exampleapp.data.network;

import com.loxboc.exampleapp.data.model.Directions;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

import static com.loxboc.exampleapp.di.module.NetworkModule.CONTENT_TYPE_JSON;

public interface GoogleApiService {
	/** @param origin lat,lng (41.43206,-81.38992)
	 *  @param destination lat,lng (41.43206,-81.38992) */
	@Headers(CONTENT_TYPE_JSON)
	@GET("/maps/api/directions/json")
	Observable<Directions> getDirections(@Query("origin") String origin,
										 @Query("destination") String destination);

	@Headers(CONTENT_TYPE_JSON)
	@GET("/maps/api/place/nearbysearch/json")
	Call<ResponseBody> getNearbyBusinesses(@Query("location") String location,
										   @Query("radius") int radiusInMeters,
										   @Query("types") String types);
}