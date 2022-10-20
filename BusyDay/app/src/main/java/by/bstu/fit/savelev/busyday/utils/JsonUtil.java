package by.bstu.fit.savelev.busyday.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.bstu.fit.savelev.busyday.models.Item;
import by.bstu.fit.savelev.busyday.models.Storage;

public class JsonUtil {
    public static boolean DeserializeDataFromJson(Context context) {
        BufferedReader br;
        try {
            if(!(new File("/data/data/by.bstu.fit.savelev.busyday/files/activityInfo.json").exists()))
                return false;
            br = new BufferedReader(new FileReader("/data/data/by.bstu.fit.savelev.busyday/files/activityInfo.json"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            ObjectMapper objectMapper = new ObjectMapper();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            ((Storage) context.getApplicationContext()).setItems(objectMapper.readValue(sb.toString(), new TypeReference<List<Item>>() {
            }));
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void SerializeDataToJson(Context context) {
        try {
            File path = context.getApplicationContext().getFilesDir();
            FileOutputStream writer = new FileOutputStream(new File(path, "activityInfo.json"));

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(out, ((Storage) context.getApplicationContext()).getItems());

            final byte[] data = out.toByteArray();
            //System.out.println(new String(data));

            writer.write(data);
            writer.close();
        } catch (Exception ex) {

        }
    }


}
