package com.loxboc.exampleapp.data.network.repository;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.loxboc.exampleapp.data.model.Directions;
import com.loxboc.exampleapp.data.model.Leg;
import com.loxboc.exampleapp.data.network.GoogleApiService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;

import static com.loxboc.exampleapp.UtilTools.getLatLngQuery;

@Singleton
public class MapDataRepositoryImpl implements MapDataRepository {
	private GoogleApiService googleApiService;

	@Inject
	MapDataRepositoryImpl(GoogleApiService googleApiService) {
		this.googleApiService = googleApiService;
	}

	@Override
	@NonNull
	@CheckResult
	public Observable<Directions> getDirections(LatLng a, LatLng b) {
		// here could network request results and local
		// data be merged and returned and much more
		return googleApiService.getDirections(getLatLngQuery(a), getLatLngQuery(b));
	}

	@Override
	@NonNull
	@CheckResult
	public Observable<List<LatLng>> getRoutePolyline(LatLng a, LatLng b) {
		return getDirections(a, b)
				.map(Directions::getRoutes)
				.flatMapIterable(routes -> {
					Log.d(MapDataRepositoryImpl.class.getSimpleName(), routes.toString());
					return routes;
				})
				.flatMapIterable(Directions.Route::getLegs)
				.flatMapIterable(Leg::getSteps)
				.flatMap(step -> {
					String points = step.getPolyline().getPoints();
					return Observable.from(PolyUtil.decode(points));
				})
				.toList();
	}

	@NonNull
	@Override
	@CheckResult
	public Call<ResponseBody> getNearbyPlaces(LatLng location) {
		return googleApiService.getNearbyBusinesses(getLatLngQuery(location), 10000, null); //"restaurant|bar|cafe");
	}
}
