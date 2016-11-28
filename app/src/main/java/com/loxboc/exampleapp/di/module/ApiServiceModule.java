package com.loxboc.exampleapp.di.module;

import com.loxboc.exampleapp.data.network.FacebookApiService;
import com.loxboc.exampleapp.data.network.GoogleApiService;
import com.loxboc.exampleapp.di.qualifier.FacebookServer;
import com.loxboc.exampleapp.di.qualifier.GoogleMapsServer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public final class ApiServiceModule {
	@Provides
	@Singleton
	GoogleApiService provideGoogleApiService(@GoogleMapsServer Retrofit retrofit) {
		return retrofit.create(GoogleApiService.class);
	}

	@Provides
	@Singleton
	FacebookApiService provideFacebookApiService(@FacebookServer Retrofit retrofit) {
		return retrofit.create(FacebookApiService.class);
	}
}