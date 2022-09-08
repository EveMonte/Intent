package by.bstu.fit.savelev.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LastActivity extends AppCompatActivity {
    Book book;
    TextView title;
    TextView genre;
    TextView description;
    TextView author;
    TextView price;
    TextView year;
    TextView audible;
    TextView pages;
    TextView length;
    TextView json;
    List<Book> store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
        title = findViewById(R.id.last_bookTitle);
        author = findViewById(R.id.last_bookAuthor);
        genre = findViewById(R.id.last_bookGenre);
        price = findViewById(R.id.last_bookPrice);
        year = findViewById(R.id.last_bookYear);
        description = findViewById(R.id.last_bookDescription);
        audible = findViewById(R.id.last_bookAudible);
        pages = findViewById(R.id.last_bookPages);
        length = findViewById(R.id.length);
        json = findViewById(R.id.json);
        if (getIntent().getSerializableExtra("Book") != null) {
            book = (Book) getIntent().getSerializableExtra("Book");
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            genre.setText(book.getGenre());
            price.setText(((Integer)book.getPrice()).toString());
            pages.setText(((Integer)book.getPages()).toString());
            year.setText(((Integer)book.getYear()).toString());
            audible.setText(((Boolean)book.isAudible()).toString());
            if(((Storage) this.getApplication()).getBookstore() != null){
                store = ((Storage) this.getApplication()).getBookstore();
                length.setText(((Integer)store.size()).toString());
            }
            else{
                store = new ArrayList<Book>();
                ((Storage) this.getApplication()).setBookstore(store);
            }
        }
    }

    public void add(View v){
        if(store != null) {
            store.add(book);
        }
        length.setText(((Integer)store.size()).toString());
        back();
    }

    public void back(){
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        switchActivityIntent.putExtra("Book", new Book());
        startActivity(switchActivityIntent);

    }

    public void serialize(View v){
        try {
            JSONObject bookJSON = new JSONObject();
            File path = getApplicationContext().getFilesDir();
            Toast filePath = Toast.makeText(getApplicationContext(), (CharSequence) path, Toast.LENGTH_LONG);
            filePath.show();
            FileOutputStream writer = new FileOutputStream(new File(path, "bookInfo.json"));
            bookJSON.put("title", book.getTitle());
            bookJSON.put("author", book.getAuthor());
            bookJSON.put("genre", book.getGenre());
            bookJSON.put("description", book.getDescription());
            bookJSON.put("pages", book.getPages());
            bookJSON.put("price", book.getPrice());
            bookJSON.put("year", book.getYear());
            bookJSON.put("audible", book.isAudible());

            String bookString = bookJSON.toString();
            writer.write(bookString.getBytes(StandardCharsets.UTF_8));
            writer.close();
            //json.setText(bookString);
        }
        catch (Exception ex){
            ex.getMessage();
            Log.d("Error", ex.getMessage());
        }
    }
}