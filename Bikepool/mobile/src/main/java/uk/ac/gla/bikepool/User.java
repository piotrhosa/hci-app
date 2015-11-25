package uk.ac.gla.bikepool;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class User {

    private int mId;
    private String mName;
    private String mThumbnailUrl;
    private Double mRank;
    private int[] mPools;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public User() {}

    public User(String name, String thumbnailUrl) {
        mId = 0;
        mName = name;
        mThumbnailUrl = thumbnailUrl;
        mRank = 0.0;
        mPools = null;
    }

    public int getId() {return mId;}

    public void setId(int id) {mId = id;}

    public String getName() {return mName;}

    public void setName(String name) {mName = name;}

    public String getThumbnailUrl() {return mThumbnailUrl;}

    public void setThumbnailUrl(String thumbnailUrl) {mThumbnailUrl = thumbnailUrl;}

    public Double getRank() {return mRank;}

    public void setRank(Double rank) {mRank = rank;}

    public int[] getPools() {return mPools;}

    public void setPools(int[] pools) {mPools = pools;}

    public void setNotification(int hour, int minute, BikePool bikePool, Context context) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReciever.class);
        intent.putExtra("startLocation",bikePool.getStartString());
        intent.putExtra("bikerName", this.getName());
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.add(Calendar.MINUTE,-20);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }
}
