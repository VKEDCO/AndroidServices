package org.vkedco.mobappdev.bindsumclient;

import org.vkedco.mobappdev.bindsumlib.IBindSumService;
import org.vkedco.mobappdev.bindsumlib.SumRequest;
import org.vkedco.mobappdev.bindsumlib.SumResponse;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BindSumActivity extends Activity implements OnClickListener, ServiceConnection {
	
	final static String LOGTAG = "BindSumActivity";
	
	EditText mEdTxtInput = null;
	Button mBtnCat = null;
	Button mBtnFib = null;
	Button mBtnCatR = null;
	Button mBtnFibR = null;
	IBindSumService mBindSumService = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_sum_activity_layout);
		mEdTxtInput = (EditText) this.findViewById(R.id.edTxtN);
		mBtnCat = (Button) this.findViewById(R.id.btnCatSum);
		mBtnFib = (Button) this.findViewById(R.id.btnFibSum);
		mBtnCatR = (Button) this.findViewById(R.id.btnCatSumR);
		mBtnFibR = (Button) this.findViewById(R.id.btnFibSumR);
		
		mBtnCat.setEnabled(false);
		mBtnCatR.setEnabled(false);
		mBtnFib.setEnabled(false);
		mBtnFibR.setEnabled(false);
		
		mBtnCat.setOnClickListener(this);
		mBtnCatR.setOnClickListener(this);
		mBtnFib.setOnClickListener(this);
		mBtnFibR.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Bind to the IBindSumService. If the service does not exist, it is created.
		
		Log.d(LOGTAG, IBindSumService.class.getName());
		
		if (!super.bindService(new Intent(IBindSumService.class.getName()), this, BIND_AUTO_CREATE)) {
			Log.d(LOGTAG, "Service could not be bound...");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		super.unbindService(this);
	}

	public void onServiceConnected(ComponentName name, IBinder service) {
		Log.d(LOGTAG, "connected to " + name);
		mBindSumService = IBindSumService.Stub.asInterface(service);
		mBtnCat.setEnabled(true);
		mBtnCatR.setEnabled(true);
		mBtnFib.setEnabled(true);
		mBtnFibR.setEnabled(true);
	}

	public void onServiceDisconnected(ComponentName name) {
		this.mBindSumService = null;
		// disable all buttons
		mBtnCat.setEnabled(false);
		mBtnCatR.setEnabled(false);
		mBtnFib.setEnabled(false);
		mBtnFibR.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bind_sum, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		final long n;
		String s = this.mEdTxtInput.getText().toString();
		if ( TextUtils.isEmpty(s) ) {
			return;
		}
		
		try {
			n = Long.parseLong(s);
		} catch (NumberFormatException e) {
			this.mEdTxtInput.setError("Format Error");
			return;
		}
		
		Log.d(LOGTAG, "input " + v.getId());
		switch ( v.getId() ) {
			case R.id.btnCatSum: handleCatSum(n); break;
			case R.id.btnFibSum: handleFibSum(n); break;
			case R.id.btnCatSumR: handleCatSumR(n); break;
			case R.id.btnFibSumR: handleFibSumR(n); break;
		}
	}
	
	private final void handleCatSum(long n) {
		try {
			final long rslt = this.mBindSumService.catalanSum(n);
			Toast.makeText(this, "Cat Sum = " + rslt, Toast.LENGTH_LONG).show();
		} catch (RemoteException e) {
			Log.d(LOGTAG, e.toString());
		}
	}
	
	private final void handleFibSum(long n) {
		try {
			final long rslt = this.mBindSumService.fibonacciSum(n);
			Toast.makeText(this, "Fib Sum = " + rslt, Toast.LENGTH_LONG).show();
		} catch (RemoteException e) {
			Log.d(LOGTAG, e.toString());
		}
	}
	
	private final void handleCatSumR(long n) {
		try {
			SumRequest sreq  = new SumRequest(n, SumRequest.SumType.CAT);
			SumResponse sres = this.mBindSumService.sum(sreq);
			Toast.makeText(this, "Sum CatR = " + sres.getSum(), Toast.LENGTH_LONG).show();
		} catch (RemoteException e) {
			Log.d(LOGTAG, e.toString());
		}
	}
	
	private final void handleFibSumR(long n) {
		try {
			SumRequest sreq  = new SumRequest(n, SumRequest.SumType.FIB);
			SumResponse sres = this.mBindSumService.sum(sreq);
			Toast.makeText(this, "Sum FibR = " + sres.getSum(), Toast.LENGTH_LONG).show();
		} catch (RemoteException e) {
			Log.d(LOGTAG, e.toString());
		}
		
	}
}
