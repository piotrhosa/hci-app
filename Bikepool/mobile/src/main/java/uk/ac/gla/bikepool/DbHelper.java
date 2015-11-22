package uk.ac.gla.bikepool;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.location.Location;
import android.content.ContentValues;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BIKEPOOL_DB";
    private static final int DATABASE_VERSION = 1;

    private static final String POOLS_TABLE = "pools";
    private static final String USERS_TABLE = "users";

    private static final String[] POOLS_COLS = {"id", "name", "startLocationLon", "startLocationLat",
        "finishLocationLon", "finishLocationLat", "membersNo", "weekDays", "startTime", "duration"};

    private static final String[] USERS_COLS = {"id", "name", "thumbnailUrl", "rank"};

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        String CREATE_POOL_TABLE = "CREATE TABLE " + POOLS_TABLE +" ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "startLocationLon DOUBLE, " +
                "startLocationLat DOUBLE, " +
                "finishLocationLon DOUBLE, " +
                "finishLocationLat DOUBLE, " +
                "membersNo INTEGER, " +
                "weekDays TEXT, " +
                "startTime TEXT, " +
                "duration INTEGER )";

        String CREATE_USER_TABLE = "CREATE TABLE " + USERS_TABLE + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "thumbnailUrl TEXT, " +
                "rank DOUBLE )";

        db.execSQL(CREATE_POOL_TABLE);
    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}

    public void addPool(BikePool pool) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", pool.getName());
        values.put("starLocationLon", pool.getStartLocation().getLongitude());
        values.put("startLocationLat", pool.getStartLocation().getLatitude());
        values.put("finishLocationLon", pool.getFinishLocation().getLongitude());
        values.put("finishLocationLat", pool.getFinishLocation().getLatitude());
        values.put("membersNo", pool.getMembersNo());
        values.put("weekDays", pool.getWeekDaysString());
        values.put("startTime", pool.getStartTime());
        values.put("duration", pool.getDuration());

        db.insert(POOLS_TABLE, null, values);
        db.close();
    }

    public void getPool(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(POOLS_TABLE, // a. table
                        POOLS_COLS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        if (cursor != null) cursor.moveToFirst();
        BikePool pool = new BikePool(cursor.getString(1));
        pool.setId(Integer.parseInt(cursor.getString(0)));
        pool.setName(cursor.getString(2));
        Location start = new Location("noProvider");
        start.setLongitude(Double.parseDouble(cursor.getString(3)));
        start.setLatitude(Double.parseDouble(cursor.getString(4)));
        Location finish = new Location("noProvider");
        finish.setLongitude(Double.parseDouble(cursor.getString(5)));
        finish.setLatitude(Double.parseDouble(cursor.getString(6)));
        pool.setStartLocation(start);
        pool.setFinishLocation(finish);
        pool.setMembersNo(Integer.parseInt(cursor.getString(7)));
        String[] daysArray = cursor.getString(8).split("(?!^)");
        int[] intArray = new int[daysArray.length];
        for(int i = 0; i < daysArray.length; ++i) intArray[i] = Integer.parseInt(daysArray[i]);
        pool.setWeekDays(intArray);
        pool.setStartTime(cursor.getString(9));
        pool.setDuration(cursor.getString(10));
    }

    public void getAllPools() {
        // TODO
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", user.getName());
        values.put("thumbnailUrl", user.getThumbnailUrl());
        values.put("rank", user.getRank());

        db.insert(USERS_TABLE, null, values);
        db.close();
    }


}