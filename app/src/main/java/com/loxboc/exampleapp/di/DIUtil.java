package com.loxboc.exampleapp.di;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.loxboc.exampleapp.ExampleApplication;

public final class DIUtil {
	private DIUtil() {
		throw new AssertionError("no instances");
	}

	@NonNull
	@CheckResult
	public static DependencyGraphProvider diGraphProvider(@NonNull Context context) {
		return ((ExampleApplication) context.getApplicationContext());
	}

	@NonNull
	@CheckResult
	public static AppComponent appInjectionProvider(@NonNull Context context) {
		return diGraphProvider(context).appComponent();
	}
}