package by.bstu.fit.savelev.intent;

import android.app.Application;

import java.util.List;

public class Storage extends Application {
    List<Book> bookstore;

    public List<Book> getBookstore() {
        return bookstore;
    }

    public void setBookstore(List<Book> bookstore) {
        this.bookstore = bookstore;
    }
}
