package by.bstu.fit.savelev.intent;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.fasterxml.jackson.databind.*;

public class LastActivity extends AppCompatActivity {
    Book book;
    ListView books;
    ArrayList<Book> store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
        books = findViewById(R.id.booksList);
        if (getIntent().getSerializableExtra("Book") != null) {
            book = (Book) getIntent().getSerializableExtra("Book");
        }
        if(((Storage) this.getApplication()).getBookstore() != null){
            store = ((Storage) this.getApplication()).getBookstore();
            fillListView();
        }
        else{
            store = new ArrayList<Book>();
            ((Storage) this.getApplication()).setBookstore(store);
        }

    }

    private void fillListView() {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;

        for(int i = 0; i < store.size(); i++){
            map = new HashMap<>();
            map.put("Title", store.get(i).getTitle());
            map.put("Author", store.get(i).getAuthor());
            arrayList.add(map);

        }
        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                new String[]{"Title", "Author"},
                new int[]{android.R.id.text1, android.R.id.text2});
        books.setAdapter(adapter);
        Intent switchActivityIntent = new Intent(this, BookInfo.class);

        books.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                switchActivityIntent.putExtra("Book", store.get(position));
                startActivity(switchActivityIntent);

            }
        });

    }


    public void add(View v){
        if(store != null) {
            store.add(book);
        }
    }
    public void refresh(View v){
        finish();
        startActivity(getIntent());
    }

    public void back(View v){
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        switchActivityIntent.putExtra("Book", new Book());
        startActivity(switchActivityIntent);

    }

    public void writeListToJsonArray(View v) {
        try {
            File path = getApplicationContext().getFilesDir();
            //CharSequence pathChar = path.;
            Toast filePath = Toast.makeText(getApplicationContext(), path.getPath(), Toast.LENGTH_LONG);
            filePath.show();
            FileOutputStream writer = new FileOutputStream(new File(path, "bookInfo.json"));

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(out, store);

            final byte[] data = out.toByteArray();
            //System.out.println(new String(data));

            writer.write(data);
            writer.close();
        }
        catch (Exception ex){

        }
    }

}