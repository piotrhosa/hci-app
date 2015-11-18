package uk.ac.gla.bikepool;

import android.location.Location;

public class BikePool {

    private String mName;
    private Location mStartLocation;
    private Location mFinishLocation;
    private int mMembersNo;

    public BikePool(String name) {
        mName = name;
        mStartLocation = null;
        mFinishLocation = null;
        mMembersNo = 0;
    }

    public String getName() {return mName;}

    public void setName(String name) {mName = name;}

    public Location getStartLocation() {return mStartLocation;}

    public void setStartLocation(Location startLocation) {mStartLocation = startLocation;}

    public Location getFinishLocation() {return mFinishLocation;}

    public void setmFinishLocation(Location finishLocation) {mFinishLocation = finishLocation;}

    public int getMembersNo() {return mMembersNo;}

    public void setMembersNo(int membersNo) {mMembersNo = membersNo;}
}
