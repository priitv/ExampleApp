package com.loxboc.exampleapp.data.model;

import java.util.List;

public class Leg {
	private List<Step> steps;

	public List<Step> getSteps() {
		return steps;
	}

	public static class Step {
		private Polyline polyline;

		public Polyline getPolyline() {
			return polyline;
		}
	}
}
