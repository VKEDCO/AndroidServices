package org.vkedco.android.localcatalannumberservice;

import android.app.Application;

public class CatalanNumberServiceApp extends Application {
	
	int mOnStartCommandReturn = android.app.Service.START_STICKY;
	
	public CatalanNumberServiceApp() {}

	public void setStartSticky() {
		mOnStartCommandReturn = android.app.Service.START_STICKY;
	}
	
	public void setStartNotSticky() {
		mOnStartCommandReturn = android.app.Service.START_NOT_STICKY;
	}
	
	public int getOnStartCommandReturn() {
		return mOnStartCommandReturn;
	}
	
}
