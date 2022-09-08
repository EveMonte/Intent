package by.bstu.fit.savelev.intent;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    EditText title;
    EditText author;
    EditText year;
    EditText genre;
    RadioButton audible;
    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.editText_bookTitle);
        author = findViewById(R.id.editText_bookAuthor);
        year = findViewById(R.id.editText_bookYear);
        genre = findViewById(R.id.editText_bookGenre);
        audible = findViewById(R.id.audible);
        if(getIntent().getSerializableExtra("Book") != null){
            book = (Book) getIntent().getSerializableExtra("Book");
            fillFields();
        }
        else{
            book = new Book();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void fillFields() {
        try{
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        if(book.getYear() != 0){
            year.setText(((Integer)book.getYear()).toString());
        }
        else{
            year.setText("");
        }
        genre.setText(book.getGenre());
        audible.setChecked(book.isAudible());
        }
        catch(Exception ex){

        }
    }

    public void switchToTheSecond(View v){
//        Intent i = new Intent(MainActivity.this,SecondActivity.class);
//        startActivity(i);
        int yearInt;
        try{
            book.setYear(Integer.parseInt(year.getText().toString()));
        }
        catch(NumberFormatException ex){
            book.setYear(0);
        }
        book.setTitle(title.getText().toString());
        book.setAuthor(author.getText().toString());
        book.setGenre(genre.getText().toString());
        book.setAudible(audible.isChecked());
        Intent switchActivityIntent = new Intent(this, SecondActivity.class);
        switchActivityIntent.putExtra("Book", book);
        startActivity(switchActivityIntent);

    }
}