package com.loxboc.exampleapp.data.network.repository;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.loxboc.exampleapp.data.model.Directions;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;

public interface MapDataRepository {
	@NonNull
	@CheckResult
	Observable<Directions> getDirections(LatLng a, LatLng b);

	@NonNull
	@CheckResult
	Observable<List<LatLng>> getRoutePolyline(LatLng a, LatLng b);

	@NonNull
	@CheckResult
	Call<ResponseBody> getNearbyPlaces(LatLng location);
}
