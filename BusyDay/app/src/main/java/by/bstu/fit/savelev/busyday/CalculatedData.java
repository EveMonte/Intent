package by.bstu.fit.savelev.busyday;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import by.bstu.fit.savelev.busyday.databinding.FragmentCalculatedDataBinding;
import by.bstu.fit.savelev.busyday.databinding.FragmentFirstBinding;
import by.bstu.fit.savelev.busyday.models.ActivityCategories;
import by.bstu.fit.savelev.busyday.models.Item;
import by.bstu.fit.savelev.busyday.models.Storage;

public class CalculatedData extends Fragment {

    private FragmentCalculatedDataBinding binding;
    private ArrayList<HashMap<String, String>> hashMap;
    private ArrayList<Item> activities;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        binding = FragmentCalculatedDataBinding.inflate(inflater, container, false);
        activities = ((Storage) getActivity().getApplicationContext()).getItems();
        hashMap = Storage.countActivitiesTotalDuration(activities);

        fillListView();
        return binding.getRoot();
    }

    public void fillListView() {
        SimpleAdapter adapter = new SimpleAdapter(getContext(), hashMap, android.R.layout.simple_list_item_2,
                new String[]{"Category", "Duration"},
                new int[]{android.R.id.text1, android.R.id.text2});
        binding.categoriesList.setAdapter(adapter);
        Log.d("a", "d");


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //fillListView(((MainActivity)getActivity()).hashMap);
    }


}