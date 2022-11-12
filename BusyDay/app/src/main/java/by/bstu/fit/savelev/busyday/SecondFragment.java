package by.bstu.fit.savelev.busyday;

import static android.app.Activity.RESULT_OK;

import static by.bstu.fit.savelev.busyday.utils.JsonUtil.SerializeDataToJson;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import by.bstu.fit.savelev.busyday.models.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import by.bstu.fit.savelev.busyday.databinding.FragmentSecondBinding;
import by.bstu.fit.savelev.busyday.utils.DbHelper;
import by.bstu.fit.savelev.busyday.utils.Orientation;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private Item newActivity;
    private ArrayList<Item> items;
    private  String imgPath;
    private int receivedItem = -1;
    private DialogSave dlg;
    OutputStream outStream = null;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedItem = bundle.getInt("CurrentItem"); // Key

        }
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        dlg = new DialogSave();
        if(((Storage) getContext().getApplicationContext()).getItems() != null){
            items = ((Storage) getContext().getApplicationContext()).getItems();
        }
        else
            items = new ArrayList<Item>();

        binding.BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        binding.BSaveItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(v.getContext(),v);
                        popup.inflate(R.menu.popup_menu);
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                // Toast message on menu item clicked
                                switch (menuItem.getItemId()) {
                                    case R.id.BConfirm:
                                        saveItem();
                                        return true;
                                    case R.id.BCancel:
                                        return true;
                                }

                                return true;
                            }
                        });
                    }
                });


        return binding.getRoot();

    }


    public void saveItem(){

        Item item = new Item();
        item.setActivityDescription(binding.activityDescriptionEdit.getText().toString());
        item.setActivityCategory((String)binding.activityCategory.getSelectedItem());
        item.setPhoto(imgPath);
        item.setDurationInMinutes(Integer.parseInt(binding.activityDurationEdit.getText().toString()));
        item.setActivityName(binding.activityNameEdit.getText().toString());

        ContentValues values = new ContentValues();
        values.put(DBContract.DBEntry.COLUMN_NAME_NAME, item.getActivityName());
        values.put(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION, item.getActivityDescription());
        values.put(DBContract.DBEntry.COLUMN_NAME_DURATION, item.getDurationInMinutes());
        values.put(DBContract.DBEntry.COLUMN_NAME_IMAGE, item.getPhoto());
        values.put(DBContract.DBEntry.COLUMN_NAME_CATEGORY, item.getActivityCategory().getValue());

        if(receivedItem >= 0){
// Столбец, коорый надо обновлять
            String selection = DBContract.DBEntry._ID + " = ?";
            String[] selectionArgs = { Long.toString(item.getId()) };
            Storage.repository.Update(values, selection, selectionArgs);
            items.set(receivedItem, item);

            ((Storage)getContext().getApplicationContext()).setItems(items);
        }
        else {
            items.add(item);

            item.setId(Storage.repository.Add(values));
        }



        FirstFragment fragment = new FirstFragment();
        if (!Orientation.isHorizontalOrientation(getActivity())) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commit();

        }

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> ct = new ArrayList<String>();
        newActivity = new Item();
        int i = 0;
        ActivityCategories[] possibleValues = ActivityCategories.values();
        for (ActivityCategories cat:
                possibleValues
        ) {
            ct.add(cat.getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                ct.toArray());
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        binding.activityCategory.setAdapter(adapter);
        if(receivedItem != -1){
            Item item = items.get(receivedItem);
            binding.activityImage.setImageURI(Uri.parse(item.getPhoto()));
            binding.activityCategory.setSelection(ct.indexOf(item.getActivityCategory().getValue()));
            binding.activityDurationEdit.setText(Integer.toString(item.getDurationInMinutes()));
            binding.activityNameEdit.setText(item.getActivityName());
            binding.activityDescriptionEdit.setText(item.getActivityDescription());
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    void imageChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    Uri selectedImageUri = data.getData();
                    // Get the path from the Uri
                    // Set the image in ImageView
                    binding.activityImage.setImageURI(selectedImageUri);
                    imgPath = selectedImageUri.toString();

                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }


}