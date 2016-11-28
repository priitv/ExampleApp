package com.loxboc.exampleapp.view;

import android.support.annotation.LayoutRes;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.loxboc.exampleapp.di.DependencyGraphProvider;

import rx.Subscription;

public interface ContentViewContract {
	@MainThread
	@LayoutRes
	int getViewResId();

	void loadDependencies(@NonNull DependencyGraphProvider provider);

	void addSubscriptionForLife(@NonNull Subscription subscription);
	void removeSubscriptionForLife(@NonNull Subscription subscription);
}
