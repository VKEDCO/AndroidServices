package org.vkedco.android.localcatalannumberservice;

/*
 *************************************************************
 * The started service of an application that computes Catalan
 * numbers and displays them one by one in the status bar
 * via a NotificationManager object. Below are the first 10 
 * Catalan numbers.
 * 
 * 0 | 1 | 2 | 3 | 4  | 5  |  6  |  7  |  8   |  9   | 10    |
 * -----------------------------------------------------------
 * 1 | 1 | 2 | 5 | 14 | 42 | 132 | 429 | 1430 | 4862 | 16796 |
 * -----------------------------------------------------------
 * 
 * Reference: http://en.wikipedia.org/wiki/Catalan_number
 * 
 * Bugs, comments to vladimir dot kulyukin at gmail dot com
 *************************************************************
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

public class CatalanNumberService extends Service {
	private long mPrevCatNum = 1L;
	private long mUpperBound = 100000L;
	private String mMsg = "";
	private long mSleepInterval = 10000L; 
	private Thread mWorkerThread = null;
	private CatalanNumberServiceApp mThisApp = null;
	
	final String SERVICE_NAME = "CatalanNumberService";
	final String SERVICE_STARTED_MSG = "starting background catalan service";
	final String SERVICE_STOPPED_MSG = "stopping background catalan service";
	final String SERVICE_CONTENT_TITLE = "Catalan Number Service";
	static final String CATALAN_NUMBERS_URI_WIKI = "http://en.wikipedia.org/wiki/Catalan_number";
	final int NOTE_ID = 112514;
	// 1/4 seconds off, 1/4 seconds on, 1/4 seconds off, 1/4 seconds - on
	final long mVibrationPattern[] = new long [] {250, 250, 250, 250 };
	private NotificationManager mNoteMngr = null;
	private Notification.Builder mNoteBuilder = null;
	private PendingIntent mPendingIntent = null;
	
	// 1. Define a worker thread that implements the Runnable
	//    interface. This is just an example of how one can spawn
	//    a thread in a service. IntentService and Looper are
	//    safer alternatives. 
	class CatalanServiceWorker implements Runnable {
		public void run() {
			long n = 0L;
			while ( mPrevCatNum <= mUpperBound ) {
				mMsg = "";
				mMsg += ("C(" + n + ")=" + mPrevCatNum);
				CatalanNumberService.this.handleNotificationMessage(mMsg);
				// Go to sleep to simulate a time-consuming computation
				try {
					Thread.sleep(mSleepInterval);
				}
				catch ( Exception ex ) {
					// Stop the service in case of exception
					CatalanNumberService.this.stopSelf();
					killWorkerThread();
				}
				nextC(++n);
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// This is a started (local) service so null is returned in onBind().
		// If it were a bound service, we would have to return an
		// IBinder.
		return null;
	}

	public void onCreate() {
		super.onCreate();
		// Get a NotificationManager object from Android
		mNoteMngr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNoteBuilder = new Notification.Builder(getApplicationContext());
		mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, 
													new Intent(Intent.ACTION_VIEW, 
													Uri.parse(CATALAN_NUMBERS_URI_WIKI)), 
													0);
		mThisApp = (CatalanNumberServiceApp) this.getApplication();
		// Post a message on the status bar that the service has been
		// started
		handleNotificationMessage(SERVICE_STARTED_MSG);
		// Create and start a worker thread.
		
		mWorkerThread = new Thread( new CatalanServiceWorker(), SERVICE_NAME );
		// Get off the main thread
		mWorkerThread.start();	
	}

	// Start a sticky thread
	public int onStartCommand(Intent i, int flags, int startId) {
		super.onStartCommand(i, flags, startId);
		//return START_STICKY;
		return mThisApp.getOnStartCommandReturn();
	}

	// This method is called by Android when the service is destroyed
	public void onDestroy() {
		super.onDestroy();
		// Post a notification message in the status bar
		handleNotificationMessage(SERVICE_STOPPED_MSG);
		// Stop the service
		stopSelf();
		// Remove icon from the status bar; does not work
		mNoteMngr.cancel(NOTE_ID);
		// wait a second to make sure that the message has a chance
		// to show on the status bar
		synchronized (this ) {
			try {
				wait(1000);
			}
			catch ( Exception ex ) {}
		}
		
		killWorkerThread();

	}
	
	// Post a notification message in the status bar
	@SuppressWarnings("deprecation")
	public void handleNotificationMessage(String msg) {
		
		mNoteBuilder.setSmallIcon(R.drawable.ornament_00)
				    .setTicker(msg)
				    .setWhen(System.currentTimeMillis())
				    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				    .setVibrate(mVibrationPattern);
		
		Notification note = mNoteBuilder.getNotification();

		note.setLatestEventInfo(getApplicationContext(), SERVICE_CONTENT_TITLE, msg, 
				mPendingIntent);
		
		this.mNoteMngr.notify(NOTE_ID, note);
	}

	// Compute the next Catalan number.
	private void nextC(long n) {
		if ( n > 0L ) {
			mPrevCatNum *= (4 * n - 2);
			mPrevCatNum /= (n + 1);
		}
	}
	
	private final void killWorkerThread() {
		mNoteMngr.cancel(NOTE_ID);
		try {
			mWorkerThread.interrupt();
		}
		catch ( Exception ex ) {
			mWorkerThread = null;
		}
	}
}
