package com.loxboc.exampleapp.di.module;

import android.app.Application;

import com.loxboc.exampleapp.ExampleApplication;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {
	private final ExampleApplication application;

	public ApplicationModule(ExampleApplication application) {
		this.application = application;
	}

	@Provides
	ExampleApplication provideTypedApplication() {
		return this.application;
	}

	@Provides
	Application provideBaseApplication() {
		return this.application;
	}

	// other application related services can be provided here like AlarmManager or CameraManager
}