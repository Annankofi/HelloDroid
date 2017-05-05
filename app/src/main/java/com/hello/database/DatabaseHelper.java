package com.hello.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashSet;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    // database name saved in data/data/<package>/databases
    private static final String DB_NAME = "Anann.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_MUSIC="music";
    private static final String TABLE_MOVIE="movie";
    
    //put the allowed table
    private static final HashSet<String> mValidTables = new HashSet<String>();
    
    static {
        mValidTables.add(MusicTable.TABLE_NAME);
        mValidTables.add(TABLE_MOVIE);
    }
    
    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MusicTable.TABLE_NAME + " (" + MusicTable.Columns._ID
                + " INTEGER PRIMARY KEY," + MusicTable.Columns.PATH + " TEXT," + MusicTable.Columns.DURATION
                + " INTEGER" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Logs that the database is being upgraded
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // drop the table and existing data
        db.execSQL("DROP TABLE IF EXISTS " + MusicTable.TABLE_NAME);
        // Recreates the database with a new version
        onCreate(db);
    }

    
    /**
     * is the table is allowed
     * @param name
     * @return
     */
    public static boolean isValidTable(String name) {
        return mValidTables.contains(name);
    }
    
}
