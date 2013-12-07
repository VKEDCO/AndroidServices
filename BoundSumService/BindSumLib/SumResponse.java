package org.vkedco.mobappdev.bindsumlib;

import android.os.Parcel;
import android.os.Parcelable;

public class SumResponse implements Parcelable {
        
        long mSum = 0;
        
        public SumResponse(long sum) {
                mSum = sum;
        }
        
        public long getSum() { return mSum; }

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
                parcel.writeLong(mSum);
        }
        
        public static final Parcelable.Creator<SumResponse> CREATOR = new Parcelable.Creator<SumResponse>() {
                // both these methods must be implemented
                public SumResponse createFromParcel(Parcel in) {
                        long sum = in.readLong();
                        return new SumResponse(sum);
                }
                
                public SumResponse[] newArray(int size) {
                        return new SumResponse[size];
                }        
        };
}
