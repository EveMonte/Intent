package by.bstu.fit.savelev.intent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ContactActivity extends AppCompatActivity {
    private static final int REQUEST_GET_SINGLE_FILE = 1;
    private Book book;
    Button BSelectImage;

    // One Preview Image
    ImageView IVPreviewImage;
    String imgPath;
    TextView email;
    TextView phoneNumber;
    TextView link;
    Context context;
    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        if(getIntent().getSerializableExtra("Book") != null){
            book = (Book) getIntent().getSerializableExtra("Book");
            try{

                BSelectImage = findViewById(R.id.BSelectImage);
                IVPreviewImage = findViewById(R.id.IVPreviewImage);
                email = findViewById(R.id.email);
                phoneNumber = findViewById(R.id.phone);
                link = findViewById(R.id.link);
                // handle the Choose Image button to trigger
                // the image chooser function
                BSelectImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageChooser();
                    }
                });
            }
            catch (Exception ex){

            }
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imgPath = savedInstanceState.getString("URI");
        IVPreviewImage.setImageURI(Uri.parse(imgPath));

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("URI", imgPath);
    }

    void imageChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        context = this;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_GET_SINGLE_FILE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    File newFile = new File(selectedImageUri.getPath());
                    newFile.getPath();
                    // Get the path from the Uri
                    imgPath = getRealPathFromURI(selectedImageUri);
                    // Set the image in ImageView
                    ((ImageView) findViewById(R.id.IVPreviewImage)).setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    public void back(View v){
        switchActivity(1);

    }
    public void next(View v){
        switchActivity(0);

    }
    @SuppressLint("Range")
    public String getRealPathFromURI(Uri contentUri) {
// can post image
        String res = null;
        if (contentUri.getScheme().equals("content")) {
            Cursor cursor = ((Context) this).getContentResolver().query(contentUri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
            if (res == null) {
                res = contentUri.getPath();
                int cutt = res.lastIndexOf('/');
                if (cutt != -1) {
                    res = res.substring(cutt + 1);
                }
            }
        }
        return res;

    }

    private void switchActivity(int direction) {
        Intent switchActivityIntent;
        book.setEmail(email.getText().toString());
        book.setLink(link.getText().toString());
        book.setPhoneNumber(phoneNumber.getText().toString());
        book.setImage(imgPath);
        switch (direction) {
            case 0:
                switchActivityIntent = new Intent(this, LastActivity.class);
                break;
            case 1:
                switchActivityIntent = new Intent(this, SecondActivity.class);
                break;
            default:
                switchActivityIntent = new Intent(this, ContactActivity.class);
                break;
        }

        switchActivityIntent.putExtra("Book", book);
        startActivity(switchActivityIntent);
    }

}