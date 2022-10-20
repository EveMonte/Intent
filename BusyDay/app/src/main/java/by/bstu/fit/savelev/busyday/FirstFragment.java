package by.bstu.fit.savelev.busyday;

import static by.bstu.fit.savelev.busyday.utils.JsonUtil.DeserializeDataFromJson;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import by.bstu.fit.savelev.busyday.models.Item;
import by.bstu.fit.savelev.busyday.models.Storage;
import by.bstu.fit.savelev.busyday.utils.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.HashMap;

import by.bstu.fit.savelev.busyday.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<Item> activities;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        if(DeserializeDataFromJson(getContext())){
            activities = ((Storage) getContext().getApplicationContext()).getItems();

        }
        else
            activities = new ArrayList<>();
        fillListView();
        registerForContextMenu(binding.booksList);
        binding.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }

        });

        return binding.getRoot();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(
                R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.view:
                viewItem(info.position);
                return true;
            case R.id.edit:
                editItem(info.position); // метод, выполняющий действие при редактировании пункта меню
                return true;
            case R.id.delete:
                deleteItem(info.position); //метод, выполняющий действие при удалении пункта меню
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void editItem(int position) {
        SecondFragment fragment = new SecondFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("CurrentItem", activities.get(position));  // Key, value
        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                //.replace(R.id.container, fragment)
                .commit();
//        NavHostFragment.findNavController(FirstFragment.this)
//                .navigate(R.id.action_FirstFragment_to_SecondFragment);

    }
    private void deleteItem(int position) {
    }

    private void viewItem(int position) {

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void fillListView() {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;

        for(int i = 0; i < activities.size(); i++){
            map = new HashMap<>();
            map.put("Name", activities.get(i).getActivityName());
            map.put("Category", activities.get(i).getActivityCategory().getValue());
            arrayList.add(map);

        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(), arrayList, android.R.layout.simple_list_item_2,
                new String[]{"Name", "Category"},
                new int[]{android.R.id.text1, android.R.id.text2});
        binding.booksList.setAdapter(adapter);

        binding.booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
//                switchActivityIntent.putExtra("Book", activities.get(position));
//                startActivity(switchActivityIntent);

            }
        });

    }


}