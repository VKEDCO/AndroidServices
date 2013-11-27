package org.vkedco.android.localcatalannumberservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/*
 *************************************************************
 * The main activity of an application with a started service 
 * that computes Catalan numbers. Below are the first 10 Catalan 
 * numbers.
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

public class StartedCatalanServiceAct extends Activity {
	
	private StartedCatalanServiceAct mThisAct = null;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mThisAct = this;
        
        Button btnStartStickyService = (Button)findViewById(R.id.btnStartStickyService);
        btnStartStickyService.setOnClickListener(
        			new OnClickListener() {
						@Override
						public void onClick(View v) {
							((CatalanNumberServiceApp) mThisAct.getApplication()).setStartSticky();
							startService(new Intent(StartedCatalanServiceAct
														.this
														.getApplicationContext(),
									CatalanNumberService.class));
							
						}
        			});
        
        Button btnStartNotStickyService = (Button)findViewById(R.id.btnStartNotStickyService);
        btnStartNotStickyService.setOnClickListener(
        			new OnClickListener() {
						@Override
						public void onClick(View v) {
							((CatalanNumberServiceApp) mThisAct.getApplication()).setStartNotSticky();
							startService(new Intent(StartedCatalanServiceAct
														.this
														.getApplicationContext(),
									CatalanNumberService.class));
							
						}
        			});
        
        Button btnStopService = (Button)findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(
        			new OnClickListener() {

						@Override
						public void onClick(View v) {
							boolean rslt =
								stopService(new Intent(StartedCatalanServiceAct
														.this
														.getApplicationContext(),
										CatalanNumberService.class));
							
							Toast.makeText(StartedCatalanServiceAct
											.this
											.getApplicationContext(),
									"stopService() == " + rslt,
									Toast.LENGTH_LONG).show();
							
							//mThisAct.finish();
							
						}
        			});
        
        
        Button btnFinish = (Button)findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(
        			new OnClickListener() {

						@Override
						public void onClick(View v) {
							boolean rslt =
								stopService(new Intent(StartedCatalanServiceAct
														.this
														.getApplicationContext(),
										CatalanNumberService.class));
							
							Toast.makeText(StartedCatalanServiceAct
											.this
											.getApplicationContext(),
									"stopService() == " + rslt,
									Toast.LENGTH_LONG).show();
							
							mThisAct.finish();
							
						}
        			});
        
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(new Intent(StartedCatalanServiceAct
										.this
										.getApplicationContext(),
										CatalanNumberService.class));
	}


	@Override
	protected void onPause() {
		super.onPause();
		stopService(new Intent(StartedCatalanServiceAct
				.this
				.getApplicationContext(),
				CatalanNumberService.class));
	}


	@Override
	protected void onStart() {
		super.onStart();
		startService(new Intent(StartedCatalanServiceAct
				.this
				.getApplicationContext(),
				CatalanNumberService.class));
		
	}


	@Override
	protected void onStop() {
		super.onStop();
		stopService(new Intent(StartedCatalanServiceAct
				.this
				.getApplicationContext(),
				CatalanNumberService.class));
	}
}
