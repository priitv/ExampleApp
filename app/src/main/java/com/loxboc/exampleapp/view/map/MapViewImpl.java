package com.loxboc.exampleapp.view.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loxboc.exampleapp.ContentViewRootActivity;
import com.loxboc.exampleapp.R;
import com.loxboc.exampleapp.data.model.Place;
import com.loxboc.exampleapp.di.DependencyGraphProvider;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindBitmap;
import butterknife.BindView;
import lombok.Getter;

public class MapViewImpl extends ContentViewRootActivity<MapPresenter> implements MapView, OnMapReadyCallback {
	private GoogleMap map;
	@Getter
	private int viewResId = R.layout.activity_root;

	@BindView(R.id.status_info)
	TextView statusInfo;

	@BindBitmap(R.drawable.ic_place)
	Bitmap mapPin;
	@BindBitmap(R.drawable.ic_restaurant)
	Bitmap markerRestaurant;
	final HashMap<String, Place> placeMarkersAdded = new HashMap<>();

	private MapPresenter presenter;

	@Inject
	@Override
	protected void initializePresenter(@NonNull MapPresenter presenter) {
		this.presenter = presenter;
		presenter.hookInto(this);
	}

	@Override
	public void loadDependencies(@NonNull DependencyGraphProvider provider) {
		provider.appComponent().inject(this);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		map = googleMap;
		map.getUiSettings().setMapToolbarEnabled(false);

		LatLng tallinn = new LatLng(59.431163, 24.746430);
		map.moveCamera(CameraUpdateFactory.newLatLng(tallinn));

		map.setOnMapClickListener(latLng -> presenter.loadNearbyPlaces(latLng.latitude, latLng.longitude));
		loadExampleData();
	}

	private void loadExampleData() {
		map.setOnMapLoadedCallback(() -> {
			LatLng home = new LatLng(59.431163, 24.746430);
			LatLng steffani = new LatLng(58.383606, 24.500050);

			map.addMarker(new MarkerOptions().position(home).title("Home")
					.icon(BitmapDescriptorFactory.fromBitmap(mapPin)));
			map.addMarker(new MarkerOptions().position(steffani).title("Steffani")
					.icon(BitmapDescriptorFactory.fromBitmap(mapPin)));

			presenter.loadRoute(home, steffani);
			presenter.loadNearbyPlaces(58.918684, 24.457235);
		});
	}

	@Override
	public void drawPolyline(List<LatLng> points) {
		map.addPolyline(new PolylineOptions()
				.addAll(points)
				.width(5)
				.color(Color.BLUE));
	}

	@Override
	public void animateCameraTo(LatLng a, LatLng b) {
		map.animateCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds.builder()
				.include(a)
				.include(b)
				.build(), 200));
	}

	@Override
	public void setLoadingNearbyPlaces(boolean loading) {
		statusInfo.setText(loading ? getString(R.string.loading_places) : null);
	}

	@Override
	public void addPlaceMarker(Place place) {
		if (placeMarkersAdded.containsKey(place.getId())) return;
		placeMarkersAdded.put(place.getId(), place);
		MarkerOptions markerOptions = new MarkerOptions()
				.position(place.getLocation())
				.title(place.getName())
				.icon(BitmapDescriptorFactory.fromBitmap(markerRestaurant));
		Marker marker = map.addMarker(markerOptions);
		presenter.loadMarkerIcon(place.getImageUrl(), marker);
	}
}
