package uk.ac.gla.bikepool;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BIKEPOOL_DB";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        String CREATE_POOL_TABLE = "CREATE TABLE pools ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "author TEXT )";

        // create books table
        //db.execSQL(CREATE_BOOK_TABLE);
    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}

    public void addPool(BikePool pool) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // TODO

    }

}
