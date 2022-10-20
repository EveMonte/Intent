package by.bstu.fit.savelev.intent;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class Storage extends Application {
    ArrayList<Book> bookstore;

    public ArrayList<Book> getBookstore() {
        return bookstore;
    }

    public void setBookstore(ArrayList<Book> bookstore) {
        this.bookstore = bookstore;
    }
}
