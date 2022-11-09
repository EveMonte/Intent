package by.bstu.fit.savelev.busyday.models;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public interface IRepository {
    ArrayList<Item> GetDirectoryList(String selection, String [] selectionArgs, String groupBy, String having, String orderBy);
    Item GetItemById(String selection, String [] selectionArgs, String groupBy, String having, String orderBy);
    long Add(ContentValues values);
    int Update(ContentValues values, String selection, String[] selectionArgs);
    int Delete(String selection, String[] selectionArgs);

}
