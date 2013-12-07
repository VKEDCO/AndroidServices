package org.vkedco.mobappdev.bindsumservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BindSumService extends Service {
	
	private static final String LOGTAG = "BindSumService";
	private IBindSumServiceImpl mServiceImpl = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.mServiceImpl = new IBindSumServiceImpl();
		Log.d(LOGTAG, "service onCreate()");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return this.mServiceImpl;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		this.mServiceImpl = null;
		super.onDestroy();
	}

}
