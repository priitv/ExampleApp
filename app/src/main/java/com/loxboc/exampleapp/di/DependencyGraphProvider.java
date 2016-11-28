package com.loxboc.exampleapp.di;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

public interface DependencyGraphProvider {
	@NonNull
	@CheckResult
	AppComponent appComponent();
}