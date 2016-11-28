package com.loxboc.exampleapp.view;

import android.support.annotation.NonNull;

public interface PresenterContract<ViewType extends ContentViewContract> {
	void hookInto(@NonNull ViewType view);

	void destroy();
}