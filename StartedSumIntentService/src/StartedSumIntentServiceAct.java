package org.vkedco.android.startedsumintentservice;

/*
 *************************************************************
 * The main activity of an application that computes the sum 
 * of the first n Catalan numbers and displays them one by one 
 * in the status bar via a NotificationManager object. Below 
 * are the first 10 Catalan numbers.
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

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartedSumIntentServiceAct extends Activity implements
		OnClickListener {
	private Button mCatSumBtn;
	private Button mFibSumBtn;
	private Button mClearBtn;
	private EditText mNEdtTxt;
	private Resources mRes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// a click on mCatSumBtn starts an intent service
		// that computes the sum of the first n elements
		// of the catalan sequence.
		mCatSumBtn = (Button) findViewById(R.id.btnCatalanSum);
		mCatSumBtn.setOnClickListener(this);
		
		// a click on mFibSumBtn starts an intent service
		// that computes the sum of the first n elements
		// of the fibonacci sequence.
		mFibSumBtn = (Button) findViewById(R.id.btnFibSum);
		mFibSumBtn.setOnClickListener(this);
		
		mClearBtn = (Button) findViewById(R.id.btnClear);
		mClearBtn.setOnClickListener(this);
		
		mNEdtTxt = (EditText) findViewById(R.id.edtxtN);
		
		mRes = getResources();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCatalanSum:
			// 1. Create an Intent to run StartedSumIntentService.class
			Intent i1 = 
				new Intent(getApplicationContext(), StartedSumIntentService.class);
			long n1 = Long.parseLong(mNEdtTxt.getText().toString().toString());
			// 2. Put two key-value pairs (<cat_sum, true> and <n, n1>)
			// into the Intent's bundle
			i1.putExtra(mRes.getString(R.string.cat_sum), true);
			i1.putExtra(mRes.getString(R.string.n_key), n1);
			// 3. start service with the created intent
			startService(i1);
			break;
		case R.id.btnFibSum:
			// 1. Create an Intent to run StartedSumIntentService.class 
			Intent i2 = 
				new Intent(getApplicationContext(), StartedSumIntentService.class);
			// 2. Put two key-value pairs (<fib_sum, true>, <n, n2>) into
			// the Intent's bundle
			long n2 = Long.parseLong(mNEdtTxt.getText().toString().toString());
			i2.putExtra(mRes.getString(R.string.fib_sum), true);
			i2.putExtra(mRes.getString(R.string.n_key), n2);
			// 3. start service with the created intent
			startService(i2);
			break;
		case R.id.btnClear:
			mNEdtTxt.setText("");
			break;
		}

	}
}
