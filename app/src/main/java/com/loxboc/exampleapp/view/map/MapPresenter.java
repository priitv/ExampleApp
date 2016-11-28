package com.loxboc.exampleapp.view.map;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.loxboc.exampleapp.data.model.Place;
import com.loxboc.exampleapp.data.network.repository.FacebookRepository;
import com.loxboc.exampleapp.data.network.repository.MapDataRepository;
import com.loxboc.exampleapp.rx.EndObserver;
import com.loxboc.exampleapp.rx.EndlessObserver;
import com.loxboc.exampleapp.view.Presenter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapPresenter extends Presenter<MapView> {
	private MapDataRepository mapDataRepository;
	private FacebookRepository facebookRepository;
	private Picasso picasso;

	private boolean loadingNearbyPlaces = false;
	private final Set<Target> markerIconTargets = new HashSet<>(); // so garbage collector couldn't collect weak references

	@Inject
	public MapPresenter(Picasso picasso,
						MapDataRepository mapDataRepository,
						FacebookRepository facebookRepository) {
		this.picasso = picasso;
		this.mapDataRepository = mapDataRepository;
		this.facebookRepository = facebookRepository;
	}

	@Override
	public void hookInto(@NonNull MapView view) {
		super.hookInto(view);
	}

	void loadRoute(LatLng a, LatLng b) {
		addSubscription(mapDataRepository.getRoutePolyline(a, b)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new EndlessObserver<List<LatLng>>() {
					@Override
					public void onNext(List<LatLng> points) {
						view.drawPolyline(points);
					}
				})
		);
		view.animateCameraTo(a, b);
	}

	void loadNearbyPlaces(double lat, double lng) {
		if (loadingNearbyPlaces) return; // load nearby places for one place at a time
		loadingNearbyPlaces = true;
		view.setLoadingNearbyPlaces(true);
		LatLng loc = new LatLng(lat, lng);
		addSubscription(facebookRepository.getNearbyPlaces(loc)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new EndObserver<Place>() {
					@Override
					public void onSuccess(boolean success) {
						loadingNearbyPlaces = false;
						view.setLoadingNearbyPlaces(false);
					}
					@Override
					public void onNext(Place place) {
						view.addPlaceMarker(place);
					}
				})
		);
	}

	void loadMarkerIcon(String iconUrl, final Marker marker) {
		Target target = new Target() {
			@Override
			public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
				Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
				marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaledBitmap));
				markerIconTargets.remove(this);
			}
			@Override
			public void onBitmapFailed(Drawable errorDrawable) { // FIXME: 12/11/2016 implement
				markerIconTargets.remove(this);
			}
			@Override public void onPrepareLoad(Drawable placeHolderDrawable) {}
		};
		picasso.load(iconUrl).into(target);
		markerIconTargets.add(target);
	}
}
