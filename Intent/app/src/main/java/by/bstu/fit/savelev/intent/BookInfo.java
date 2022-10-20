package by.bstu.fit.savelev.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BookInfo extends AppCompatActivity {

    Book currentBook;
    TextView link;
    TextView title;
    TextView author;
    TextView genre;
    TextView description;
    TextView year;
    TextView price;
    TextView isAudible;
    TextView email;
    TextView phoneNumber;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        currentBook = (Book)getIntent().getSerializableExtra("Book");
        link = findViewById(R.id.linkOutput);
        title = findViewById(R.id.titleOutput);
        image = findViewById(R.id.imageOutput);
        author = findViewById(R.id.authorOutput);
        genre = findViewById(R.id.genreOutput);
        description = findViewById(R.id.descriptionOutput);
        year = findViewById(R.id.yearOutput);
        price = findViewById(R.id.priceOutput);
        isAudible = findViewById(R.id.audibleOutput);
        email = findViewById(R.id.emailOutput);
        phoneNumber = findViewById(R.id.phoneNumberOutput);

        title.setText(currentBook.getTitle());
        author.setText(currentBook.getAuthor());
        genre.setText(currentBook.getGenre());
        description.setText(currentBook.getDescription());
        year.setText(String.valueOf(currentBook.getYear()));
        price.setText(String.valueOf(currentBook.getPrice()));
        email.setText(currentBook.getEmail());
        phoneNumber.setText(currentBook.getPhoneNumber());
        link.setText(currentBook.getLink());
        link.setOnTouchListener(this::onTouch);
        phoneNumber.setOnTouchListener(this::onTouchContact);
        email.setOnTouchListener(this::onTouchEmail);
    }

    public void back(View v){
        Intent switchActivityIntent = new Intent(this, LastActivity.class);
        //switchActivityIntent.putExtra("Book", new Book());
        startActivity(switchActivityIntent);

    }

    public boolean onTouch(View v, MotionEvent event) {
        Intent browserIntent;
        if(currentBook.getLink() != null)
            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www." + link.getText().toString()));
        else
            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(browserIntent);
        return true;
    }
    public boolean onTouchContact(View v, MotionEvent event) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        return true;
    }
    public boolean onTouchEmail(View v, MotionEvent event) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, email.getText());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }        return true;
    }
    public boolean onTouchImage(View v) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
        return true;
    }
}