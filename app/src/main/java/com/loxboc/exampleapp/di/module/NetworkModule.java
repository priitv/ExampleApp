package com.loxboc.exampleapp.di.module;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.GsonBuilder;
import com.loxboc.exampleapp.BuildConfig;
import com.loxboc.exampleapp.R;
import com.loxboc.exampleapp.data.model.Place;
import com.loxboc.exampleapp.data.serialize.LocationDeserializer;
import com.loxboc.exampleapp.data.serialize.PlacesDeserializer;
import com.loxboc.exampleapp.di.qualifier.FacebookApi;
import com.loxboc.exampleapp.di.qualifier.FacebookServer;
import com.loxboc.exampleapp.di.qualifier.GoogleMapsApi;
import com.loxboc.exampleapp.di.qualifier.GoogleMapsServer;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ApiServiceModule.class)
public final class NetworkModule {
	public static final String CONTENT_TYPE_JSON = "Content-Type: application/json;charset=UTF-8";
	static final String TAG = NetworkModule.class.getSimpleName();
	private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024;
	private static final String AUTHENTICATION_PARAM_GOOGLE = "key";
	private static final String AUTHENTICATION_PARAM_FACEBOOK = "access_token";

	static OkHttpClient.Builder createHttpClient(final Application app) {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
		// Install an HTTP cache in the application cache directory.
		final File cacheDir = new File(app.getCacheDir(), "http");
		clientBuilder.cache(new Cache(cacheDir, DISK_CACHE_SIZE));
		if (BuildConfig.DEBUG) clientBuilder.addInterceptor(logging);
		return clientBuilder;
	}

	public static Retrofit createRetrofit(OkHttpClient client, String endpoint) {
		return new Retrofit.Builder()
				.baseUrl(endpoint)
				.client(client)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(
						new GsonBuilder()
						.registerTypeAdapter(Place[].class, new PlacesDeserializer())
						.registerTypeAdapter(LatLng.class, new LocationDeserializer())
						.create()))
				.build();
	}

	@Provides
	@Singleton
	@GoogleMapsApi
	String provideGoogleMapsApiAddress() {
		return "https://maps.googleapis.com";
	}

	@Provides
	@Singleton
	@FacebookApi
	String provideFacebookApiAddress() {
		return "https://graph.facebook.com/v2.8/";
	}

	@Provides
	OkHttpClient.Builder provideGoogleOkHttpClient(Application app) {
		return createHttpClient(app);
	}

	@Provides
	@Singleton
	@GoogleMapsServer
	Retrofit provideRetrofitForGoogleMaps(Application app, OkHttpClient.Builder clientBuilder,
										  @GoogleMapsApi String baseUrl) {
		OkHttpClient client = clientBuilder.addInterceptor(new AuthenticateInterceptor(app,
				AUTHENTICATION_PARAM_GOOGLE,
				app.getResources().getString(R.string.google_maps_key)))
				.build();
		return createRetrofit(client, baseUrl);
	}

	@Provides
	@Singleton
	@FacebookServer
	Retrofit provideRetrofitForFacebook(Application app, OkHttpClient.Builder clientBuilder,
										@FacebookApi String baseUrl) {
		OkHttpClient client = clientBuilder.addInterceptor(new AuthenticateInterceptor(app,
				AUTHENTICATION_PARAM_FACEBOOK,
				app.getResources().getString(R.string.facebook_access_token)))
				.build();
		return createRetrofit(client, baseUrl);
	}

	@Provides
	@Singleton
	Picasso providePicasso(Application app) {
		return Picasso.with(app);
	}

	private static class AuthenticateInterceptor implements Interceptor {
		private Application app;
		private String key;
		private String token;

		public AuthenticateInterceptor(Application app, String key, String token) {
			this.app = app;
			this.key = key;
			this.token = token;
		}

		@Override
		public Response intercept(Interceptor.Chain chain) throws IOException {
			Request request = chain.request();
			HttpUrl url = request.url().newBuilder()
					.addQueryParameter(key, token)
					.build();
			request = request.newBuilder().url(url).build();
			return chain.proceed(request);
		}
	}
}
