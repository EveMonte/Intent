package by.bstu.fit.savelev.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ResourceBundle;

public class SecondActivity extends AppCompatActivity {
    private EditText pages;
    private EditText description;
    private EditText price;
    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        pages = findViewById(R.id.editText_bookPages);
        description = findViewById(R.id.editText_bookDescription);
        price = findViewById(R.id.editText_bookPrice);
        if(getIntent().getSerializableExtra("Book") != null){
            book = (Book) getIntent().getSerializableExtra("Book");
            try{
                if(book.getPages() != 0){
                    pages.setText(((Integer)book.getPages()).toString());
                }
                else {
                    pages.setText("");
                }
                if(book.getPrice() != 0){
                    price.setText(((Integer)book.getPrice()).toString());
                }
                else {
                    price.setText("");
                }

                description.setText(book.getDescription());
            }
            catch (Exception ex){

            }
        }
    }

    public void back(View v){
        switchActivity(1);

    }
    private void fillBookInfo(){
        try{
            book.setPages(Integer.parseInt(pages.getText().toString()));
        }
        catch(NumberFormatException ex){
            book.setPages(0);
        }
        try{
            book.setPrice(Integer.parseInt(price.getText().toString()));
        }
        catch(NumberFormatException ex){
            book.setPrice(0);
        }
        book.setDescription(description.getText().toString());

    }
    public void next(View v){
        fillBookInfo();
        switchActivity(0);

    }

    private void switchActivity(int direction) {
        Intent switchActivityIntent;
        switch (direction) {
            case 0:
                switchActivityIntent = new Intent(this, ContactActivity.class);
                break;
            case 1:
                switchActivityIntent = new Intent(this, MainActivity.class);
                break;
            default:
                switchActivityIntent = new Intent(this, SecondActivity.class);
                break;
        }

        switchActivityIntent.putExtra("Book", book);
        startActivity(switchActivityIntent);
    }
}