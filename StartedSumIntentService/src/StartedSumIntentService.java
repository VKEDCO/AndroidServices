package org.vkedco.android.startedsumintentservice;

/*
 *************************************************************
 * The started intent service of an application that computes 
 * the sum of the first n Catalan numbers and displays them 
 * one by one in the status bar via a NotificationManager object. 
 * Below are the first 10 Catalan numbers.
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

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

public class StartedSumIntentService extends IntentService {
	private NotificationManager mNoteMngr;
	private Resources mRes;
	
	public StartedSumIntentService() {
		super("StartedSumIntentService");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mNoteMngr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mRes = getResources();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		String catSumKey = mRes.getString(R.string.cat_sum);
		String fibSumKey = mRes.getString(R.string.fib_sum);
		String nKey = mRes.getString(R.string.n_key);
		// 1. check if the bundle contains cat_sum or fib_sum key and
		//    n_key
		if ( (extras.containsKey(catSumKey) || extras.containsKey(fibSumKey))
				&&
			  extras.containsKey(nKey) )  {
			long n = extras.getLong(nKey);
			// 2. if cat_sum key is present, then compute the catalan sum
			// and post a notification message on the status bar
			if ( extras.containsKey(catSumKey) ) {
				postNotificationMessage(compileCatNote(n, catalanSum(n)));
			}
			else {
				// 3. if fibo_sum key is present, then compute the
				// fibonacci sum and post a notification message on
				// the status bar
				postNotificationMessage(compileFiboNote(n, fiboSum(n)));
			}
		}
		else {
			postNotificationMessage("Wrong arguments");
		}
		
		// pause for one second
		synchronized ( this ) {
			try {
				wait(1000L);
			}
			catch ( Exception ex ) {
				
			}
		}
		
		mNoteMngr.cancel(R.string.app_id);
	}
	
	private String compileCatNote(long n, long sum) {
		return "CatalanSum(" + n + ") = " + sum; 
	}
	
	private String compileFiboNote(long n, long sum) {
		return "FibSum(" + n + ")= " + sum;
	}
	
	private void postNotificationMessage(String msg) {
		Notification note = new Notification(R.drawable.ornament_00, msg,
				System.currentTimeMillis());
		PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),
				0, new Intent(getApplicationContext(),
						StartedSumIntentServiceAct.class), 0);
		note.setLatestEventInfo(getApplicationContext(),
				"StartedSumIntentService", msg, pi);
		this.mNoteMngr.notify(R.string.app_id, note);
	}
	
	private long catalanSum(long n) {
		if ( n == 0L ) {
			return 1L;
		}
		else {
			long prevC = 1L;
			long sum   = 1L;
			for(long i = 1; i <= n; i++) {
				prevC *= (4 * i - 2);
				prevC /= (i + 1);
				sum += prevC;
			}
			return sum;
		}
	}
	
	private long fiboSum(long n) {
		if ( n == 0L ) {
			return 1L;
		}
		else if ( n == 1L ) {
			return 2L;
		}
		else {
			long prev1 = 1L;
			long prev2 = 1L;
			long sum = 2L;
			for(int i = 2; i <= n; i++) {
				long next = prev1 + prev2;
				sum += next;
				prev1 = prev2;
				prev2 = next;
			}
			return sum;
		}
	}
}
