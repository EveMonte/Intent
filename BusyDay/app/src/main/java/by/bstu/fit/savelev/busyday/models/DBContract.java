package by.bstu.fit.savelev.busyday.models;

import android.provider.BaseColumns;

public final class DBContract {
    public DBContract() {}
    /* Внутренний класс определяет контент таблицы*/
    public static abstract class DBEntry implements BaseColumns {
        public static final String TABLE_NAME = "activity";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_IMAGE = "image";
    }
}