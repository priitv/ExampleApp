package com.loxboc.exampleapp.di;

import com.loxboc.exampleapp.ExampleApplication;
import com.loxboc.exampleapp.di.module.ApplicationModule;
import com.loxboc.exampleapp.di.module.NetworkModule;
import com.loxboc.exampleapp.di.module.RepositoryModule;
import com.loxboc.exampleapp.view.map.MapViewImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		ApplicationModule.class,
		NetworkModule.class,
		RepositoryModule.class,
})
public interface AppComponent {
	void inject(ExampleApplication application);

	void inject(MapViewImpl view);
}
