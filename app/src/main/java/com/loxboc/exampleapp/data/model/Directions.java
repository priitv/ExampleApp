package com.loxboc.exampleapp.data.model;

import java.util.List;

// FIXME: 11/11/2016 consider using AutoValue instead for readability
public class Directions {
	private List<Route> routes;

	public List<Route> getRoutes() {
		return routes;
	}

	public static class Route {
		private List<Leg> legs;

		public List<Leg> getLegs() {
			return legs;
		}
	}
}
