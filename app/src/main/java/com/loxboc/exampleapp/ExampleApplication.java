package com.loxboc.exampleapp;

import android.app.Application;
import android.support.annotation.NonNull;

import com.loxboc.exampleapp.di.AppComponent;
import com.loxboc.exampleapp.di.DaggerAppComponent;
import com.loxboc.exampleapp.di.DependencyGraphProvider;
import com.loxboc.exampleapp.di.module.ApplicationModule;

public class ExampleApplication extends Application implements DependencyGraphProvider {
	private AppComponent appComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		initializeDIRoots();
	}

	private void initializeDIRoots() {
		final AppComponent appComponent = DaggerAppComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.build();
		appComponent.inject(this);
		this.appComponent = appComponent;
	}

	@NonNull
	@Override
	public AppComponent appComponent() {
		return appComponent;
	}
}
