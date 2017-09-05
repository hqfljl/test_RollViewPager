package com.dance.rollviewpager;

import android.app.Application;

public class App extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		ImageCacheUtil.init(this);
	}
}
