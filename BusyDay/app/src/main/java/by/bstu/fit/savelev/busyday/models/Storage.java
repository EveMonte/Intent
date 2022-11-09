package by.bstu.fit.savelev.busyday.models;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import by.bstu.fit.savelev.busyday.utils.DbHelper;

public class Storage extends Application {
    ArrayList<Item> activities;
    public static Repository repository;
//    public Storage() {
//        mDbHelper = new DbHelper(this);
//        dbHelperWritableDatabase = mDbHelper.getWritableDatabase();
//        dbHelperReadableDatabase = mDbHelper.getReadableDatabase();
//
//    }

    public ArrayList<Item> getItems() {
        return activities;
    }

    public void setItems(ArrayList<Item> activities) {
        this.activities = activities;
    }

    public static ArrayList<Item> sortByCategoryName(ArrayList<Item> activities){


        Collections.sort(activities, new Comparator<Item>() {
            public int compare(Item o1, Item o2) {
                return o1.getActivityCategory().getValue().compareTo(o2.getActivityCategory().getValue());
            }
        });
        return activities;
    }

    public static ArrayList<HashMap<String, String>> countActivitiesTotalDuration(ArrayList<Item> activities){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;

        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (ActivityCategories category:
             ActivityCategories.values()) {
            map = new HashMap<>();
            map.put("Category", category.getValue());
            map.put("Duration", Integer.toString(activities.stream().
                    filter(a -> a.getActivityCategory() == category).
                    mapToInt(a -> a.getDurationInMinutes()).sum()));
            arrayList.add(map);

        }
        return arrayList;
    }
}
