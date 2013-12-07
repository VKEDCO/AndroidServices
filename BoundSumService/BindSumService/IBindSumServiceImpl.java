package org.vkedco.mobappdev.bindsumservice;

import org.vkedco.mobappdev.bindsumlib.SumRequest;
import org.vkedco.mobappdev.bindsumlib.SumResponse;
import org.vkedco.mobappdev.bindsumlib.IBindSumService;

import android.os.RemoteException;
import android.util.Log;

public class IBindSumServiceImpl extends IBindSumService.Stub {
	private static final String LOGTAG = "IBindSumServiceImpl";
	
	@Override
	public long fibonacciSum(long n) throws RemoteException {
		Log.d(LOGTAG, "fibonacciSum on service ...");
		return fibSumHelper(n);
	}
	
	@Override
	public long catalanSum(long n) throws RemoteException {
		Log.d(LOGTAG, "catalanSum on service ...");
		return catSumHelper(n);
	}

	@Override
	public SumResponse sum(SumRequest sreq) throws RemoteException {
		Log.d(LOGTAG, "sum on service ...");
		long sum = 0;
		switch ( sreq.getSumType() ) {
		case CAT:
			sum = catSumHelper(sreq.getN());
			break;
		case FIB:
			sum = fibSumHelper(sreq.getN());
			break;
		default:
			return null;
		}
		return new SumResponse(sum);
	}
	
	private static final long catSumHelper(long n) {
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
	
	private static final long fibSumHelper(long n) {
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
