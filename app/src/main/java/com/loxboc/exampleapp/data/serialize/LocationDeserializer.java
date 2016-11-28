package com.loxboc.exampleapp.data.serialize;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class LocationDeserializer implements JsonDeserializer<LatLng> {
	@Override
	public LatLng deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
			throws JsonParseException {
		JsonObject data = je.getAsJsonObject();
		double latitude = data.get("latitude").getAsDouble();
		double longitude = data.get("longitude").getAsDouble();
		return new LatLng(latitude, longitude);
	}
}