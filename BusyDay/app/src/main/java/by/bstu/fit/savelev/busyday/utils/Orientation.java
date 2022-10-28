package by.bstu.fit.savelev.busyday.utils;

import android.content.Context;
import android.content.res.Configuration;

public class Orientation {
    public static boolean isHorizontalOrientation(Context context){

        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
