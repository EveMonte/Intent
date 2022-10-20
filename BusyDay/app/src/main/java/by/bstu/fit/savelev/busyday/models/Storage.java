package by.bstu.fit.savelev.busyday.models;

import android.app.Application;

import java.util.ArrayList;

public class Storage extends Application {
    ArrayList<Item> activities;

    public ArrayList<Item> getItems() {
        return activities;
    }

    public void setItems(ArrayList<Item> activities) {
        this.activities = activities;
    }
}
