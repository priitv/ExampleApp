package com.loxboc.exampleapp.data.serialize;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.loxboc.exampleapp.data.model.Place;

import java.lang.reflect.Type;

public class PlacesDeserializer implements JsonDeserializer<Place[]> {
	@Override
	public Place[] deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
			throws JsonParseException {
		JsonArray data = je.getAsJsonObject().get("data").getAsJsonArray();
		int size = data.size();
		Place[] places = new Place[size];

		for (int i = 0; i < size; i++) {
			JsonObject placeElement = data.get(i).getAsJsonObject();
			String picture = placeElement
					.getAsJsonObject("picture")
					.getAsJsonObject("data")
					.getAsJsonPrimitive("url")
					.getAsString();
			Place place = new Gson().fromJson(placeElement, Place.class);
			place.setImageUrl(picture);
			places[i] = place;
		}
		return places;

	}
}