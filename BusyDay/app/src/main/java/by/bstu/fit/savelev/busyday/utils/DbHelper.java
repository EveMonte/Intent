package by.bstu.fit.savelev.busyday.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import by.bstu.fit.savelev.busyday.models.DBContract;

public class DbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.DBEntry.TABLE_NAME + " (" +
                    DBContract.DBEntry._ID + " INTEGER PRIMARY KEY," +
                    DBContract.DBEntry.COLUMN_NAME_NAME +" TEXT" + "," +
                    DBContract.DBEntry.COLUMN_NAME_CATEGORY +" TEXT" + "," +
                    DBContract.DBEntry.COLUMN_NAME_DURATION +" INTEGER" + "," +
                    DBContract.DBEntry.COLUMN_NAME_IMAGE +" TEXT" + "," +
                    DBContract.DBEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBContract.DBEntry.TABLE_NAME;


    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "DBSimple.db";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
