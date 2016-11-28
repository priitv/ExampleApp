package com.loxboc.exampleapp.view;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

public abstract class Presenter<ViewType extends ContentViewContract> implements PresenterContract<ViewType> {
	protected ViewType view;
	@Nullable
	private CompositeSubscription subscriptions;

	@CallSuper
	@Override
	public void hookInto(@NonNull ViewType view) {
		this.view = view;
		view.addSubscriptionForLife(Subscriptions.create(this::destroy));
		subscriptions = new CompositeSubscription();
	}

	@CallSuper
	@Override
	public void destroy() {
		if (subscriptions != null) {
			subscriptions.unsubscribe();
			subscriptions = null;
		}
		view = null;
	}

	protected final void addSubscription(@NonNull Subscription subscription) {
		if (subscriptions == null) throw new IllegalStateException("Tried to leak subscription. Check your code");
		subscriptions.add(subscription);
	}

	protected final void removeSubscription(@NonNull Subscription subscription) {
		subscription.unsubscribe();
		if (subscriptions != null) {
			subscriptions.remove(subscription);
		}
	}
}
