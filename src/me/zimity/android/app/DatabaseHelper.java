package me.zimity.android.app;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 * Helper class to assist with opening an SQLite database.
 * Automatically creates a database if one does not already exist and upgrades
 * the existing database if it discovered to be an old version.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "zimity";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_IMPRINT = "imprint";

    /*
     * Constructor
     * 
     * @param context The application context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * Creates the initial tables and populates it with some test data.
     * 
     * @param db The new database object
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDBQuery = "CREATE TABLE imprint ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "userid INTEGER NOT NULL," + "type SMALLINT NOT NULL,"
                + "caption TEXT NOT NULL," + "slug CHAR(6) NOT NULL,"
                + "latitude REAL NOT NULL," + "longitude REAL NOT NULL,"
                + "altitude REAL NOT NULL," + "bearing REAL NOT NULL,"
                + "accuracy REAL NOT NULL," + "speed REAL NOT NULL,"
                + "sharing SMALLINT NOT NULL," + "syncd BOOLEAN NOT NULL,"
                + "deleted BOOLEAN NOT NULL," + "created DATETIME NOT NULL,"
                + "modified DATETIME NOT NULL" + ");";

        db.execSQL(createDBQuery);
    }

    /*
     * Upgrades the pre-existing database if it is a lower version.
     * Currently, it simply drops the table and creates a new one. This is
     * acceptable for now since this is the initial version of the app but in the
     * future it should be able to migrate any old databases to the latest version.
     * 
     * @param db The database object
     * @param oldVersion The old database version number
     * @param newVersion The new database version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TABLE_IMPRINT, "Upgrading database will destroy all old data.");
        db.execSQL("DROP TABLE IF EXSITS " + TABLE_IMPRINT);
        onCreate(db);
    }

}
