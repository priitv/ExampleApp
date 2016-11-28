package com.loxboc.exampleapp.di.module;

import com.loxboc.exampleapp.data.network.repository.FacebookRepository;
import com.loxboc.exampleapp.data.network.repository.FacebookRepositoryImpl;
import com.loxboc.exampleapp.data.network.repository.MapDataRepository;
import com.loxboc.exampleapp.data.network.repository.MapDataRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class RepositoryModule {
	@Provides
	@Singleton
	MapDataRepository provideGoogleMapsRepository(MapDataRepositoryImpl repository) {
		return repository;
	}

	@Provides
	@Singleton
	FacebookRepository provideFacebookRepository(FacebookRepositoryImpl repository) {
		return repository;
	}

	// Other repos can be added here to pull data from network or local databases
}