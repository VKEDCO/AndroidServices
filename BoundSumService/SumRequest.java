package org.vkedco.mobappdev.bindsumlib;

import android.os.Parcel;
import android.os.Parcelable;

public class SumRequest implements Parcelable {

	public static enum SumType { CAT, FIB };
	
	long mN = 0;
	SumType mSumType = null;
	
	public SumRequest(long n, SumType st) {
		mN = n;
		mSumType = st;
	}
	
	public long getN() { return mN; }
	
	public SumType getSumType() { return mSumType; }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeLong(this.mN);
		parcel.writeInt(this.mSumType.ordinal());
	}
	
	public static final Parcelable.Creator<SumRequest> CREATOR = new Parcelable.Creator<SumRequest>() {
		// both these methods must be implemented
		public SumRequest createFromParcel(Parcel in) {
			long n = in.readLong();
			SumType st = SumType.values()[in.readInt()];
			return new SumRequest(n, st);
		}
		
		public SumRequest[] newArray(int size) {
			return new SumRequest[size];
		}	
	};
	
	
	
}
