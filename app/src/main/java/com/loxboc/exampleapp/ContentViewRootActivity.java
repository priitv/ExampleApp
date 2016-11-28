package com.loxboc.exampleapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.loxboc.exampleapp.di.DIUtil;
import com.loxboc.exampleapp.view.ContentViewContract;
import com.loxboc.exampleapp.view.PresenterContract;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class ContentViewRootActivity<PresenterType extends PresenterContract> extends FragmentActivity implements ContentViewContract {
	@NonNull
	private final CompositeSubscription lifeSubscriptions = new CompositeSubscription();

	protected abstract void initializePresenter(@NonNull PresenterType presenter);

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getViewResId());
		ButterKnife.bind(this);
		loadDependencies(DIUtil.diGraphProvider(getApplicationContext()));
	}

	@Override
	protected void onDestroy() {
		lifeSubscriptions.unsubscribe();
		super.onDestroy();
	}

	@Override
	public final void addSubscriptionForLife(@NonNull Subscription subscription) {
		if (lifeSubscriptions.isUnsubscribed())
			throw new IllegalStateException("Tried to leak subscription for life but view is already dead. Check your code");
		lifeSubscriptions.add(subscription);
	}

	@Override
	public final void removeSubscriptionForLife(@NonNull Subscription subscription) {
		subscription.unsubscribe();
		lifeSubscriptions.remove(subscription);
	}
}
