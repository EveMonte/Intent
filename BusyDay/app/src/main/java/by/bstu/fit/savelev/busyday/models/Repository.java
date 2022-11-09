package by.bstu.fit.savelev.busyday.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import by.bstu.fit.savelev.busyday.utils.DbHelper;

public class Repository implements IRepository{

    private DbHelper mDbHelper;
    private SQLiteDatabase dbHelperWritableDatabase;
    private SQLiteDatabase dbHelperReadableDatabase;
    private Context context;
    private String[] projection;

    public Repository(Context context) {
        this.context = context;
        mDbHelper = new DbHelper(context);
        dbHelperReadableDatabase = mDbHelper.getReadableDatabase();
        dbHelperWritableDatabase = mDbHelper.getWritableDatabase();
        projection = new String[]{
                BaseColumns._ID,
                DBContract.DBEntry.COLUMN_NAME_NAME,
                DBContract.DBEntry.COLUMN_NAME_DESCRIPTION,
                DBContract.DBEntry.COLUMN_NAME_CATEGORY,
                DBContract.DBEntry.COLUMN_NAME_DURATION,
                DBContract.DBEntry.COLUMN_NAME_IMAGE,
        };

    }

    public ArrayList<Item> GetDirectoryList(String selection, String [] selectionArgs, String groupBy, String having, String orderBy) {
        return GetDirectoryList(projection, selection, selectionArgs, groupBy, having, orderBy);
    }
    @Override
    public ArrayList<Item> GetDirectoryList(String[] projection, String selection, String [] selectionArgs, String groupBy, String having, String orderBy) {
        // определяем названия столбцов
        // которые нужны для выполнения запроса
        // String selection = DBContract.DBEntry.COLUMN_NAME_NAME + " = ?";
        // String[] selectionArgs = { "Ivanov" };
        // Возвращается Cursor
        // String sortOrder =
        // DBContract.DBEntry.COLUMN_NAME_NAME + " DESC";
        Cursor cursor = GetCursor(projection, selection, selectionArgs, groupBy, having, orderBy);
        ArrayList<Item> activities = new ArrayList<>();
        while (cursor.moveToNext()) {
            Item item = new Item(cursor.getLong(
                    cursor.getColumnIndexOrThrow(DBContract.DBEntry._ID)),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_NAME)),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION)),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_CATEGORY)),
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DURATION)),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_IMAGE))
            );
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DBContract.DBEntry._ID));
            activities.add(item);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return activities;

    }

    @Override
    public Cursor GetCursor(String[] projection, String selection, String [] selectionArgs, String groupBy, String having, String orderBy){
        return dbHelperWritableDatabase.query(
                DBContract.DBEntry.TABLE_NAME, // имя таблицы
                projection, // столбцы
                selection, // столбцы для WHERE
                selectionArgs, // значения для WHERE
                groupBy, // не группировать строки
                having, // не фильтровать
                orderBy // порядок сортировки
        );
    }
    public Cursor GetCursor(String selection, String [] selectionArgs, String groupBy, String having, String orderBy){
        return dbHelperWritableDatabase.query(
                DBContract.DBEntry.TABLE_NAME, // имя таблицы
                projection, // столбцы
                selection, // столбцы для WHERE
                selectionArgs, // значения для WHERE
                groupBy, // не группировать строки
                having, // не фильтровать
                orderBy // порядок сортировки
        );
    }


        @Override
    public Item GetItemById(String selection, String [] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Item> activities = GetDirectoryList(selection, selectionArgs, groupBy, having, orderBy);
        if(activities != null){
            return activities.get(0);
        }
        return null;
    }

    @Override
    public long Add(ContentValues values) {

        return dbHelperWritableDatabase.insert(
                DBContract.DBEntry.TABLE_NAME,
                null,
                values);
    }

    @Override
    public int Update(ContentValues values, String selection, String[] selectionArgs) {
        return dbHelperWritableDatabase.update(
                DBContract.DBEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    @Override
    public int Delete(String selection, String[] selectionArgs) {
        return dbHelperWritableDatabase.delete(DBContract.DBEntry.TABLE_NAME, selection,
                selectionArgs);
    }
}
