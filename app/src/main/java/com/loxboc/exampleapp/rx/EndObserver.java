package com.loxboc.exampleapp.rx;

import rx.Observer;

public abstract class EndObserver<T> implements Observer<T> {
	@Override
	public void onCompleted() {
		onSuccess(true);
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
		onSuccess(false);
	}

	@Override
	public void onNext(T t) {}

	public void onSuccess(boolean success) {}
}
