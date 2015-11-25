package uk.ac.gla.bikepool;

import android.location.Location;

public class BikePool {

    private int mId;
    private String mName;
    private Location mStartLocation;
    private Location mFinishLocation;
    private String mStartString;
    private String mFinishString;
    private int mMembersNo;
    private int[] mWeekDays;
    private String mStartTime;
    private String mDuration;

    public BikePool() {}

    public BikePool(String name) {
        mId = 0;
        mName = name;
        mStartLocation = null;
        mFinishLocation = null;
        mMembersNo = 0;
        mWeekDays = new int[7];
        mStartTime = "0:00";
        mDuration = "0:00";
        mStartString = "";
        mFinishString = "";
    }

    public BikePool(String name, Location startLocation) {
        mId = 0;
        mName = name;
        mStartLocation = startLocation;
        mFinishLocation = null;
        mMembersNo = 0;
        mWeekDays = new int[7];
        mStartTime = "0:00";
        mDuration = "0:00";
        mStartString = "";
        mFinishString = "";
    }

    public BikePool(String name, Location startLocation, Location endLocation, String startString, String finishString) {
        mId = 0;
        mName = name;
        mStartLocation = startLocation;
        mFinishLocation = endLocation;
        mMembersNo = 0;
        mWeekDays = new int[7];
        mStartTime = "0:00";
        mDuration = "0:00";
        mStartString = startString;
        mFinishString = finishString;
    }

    public int getId() {return mId;}

    public void setId(int id) {mId = id;}

    public String getName() {return mName;}

    public void setName(String name) {mName = name;}

    public Location getStartLocation() {return mStartLocation;}

    public void setStartLocation(Location startLocation) {mStartLocation = startLocation;}

    public Location getFinishLocation() {return mFinishLocation;}

    public void setFinishLocation(Location finishLocation) {mFinishLocation = finishLocation;}

    public int getMembersNo() {return mMembersNo;}

    public void setMembersNo(int membersNo) {mMembersNo = membersNo;}

    public int[] getWeekDays() {return mWeekDays;}

    public String getWeekDaysString() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < mWeekDays.length; i++)
            strBuilder.append(mWeekDays[i]);

        return strBuilder.toString();
    }

    public String getStartString() {return mStartString;}

    public String getFinishString() {return mFinishString;}

    public void setStartString(String startString) {mStartString = startString;}

    public void setFinishString(String finishString) {mFinishString = finishString;}

    public String getStartTime() {return mStartTime;}

    public String getDuration() {return mDuration;}

    public void setWeekDays(int[] weekDays) {mWeekDays = weekDays;}

    public void setStartTime(String startTime) {mStartTime = startTime;}

    public void setDuration(String duration) {mDuration = duration;}
}