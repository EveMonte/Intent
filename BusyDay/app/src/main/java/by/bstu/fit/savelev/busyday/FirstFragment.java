package by.bstu.fit.savelev.busyday;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import by.bstu.fit.savelev.busyday.models.DBContract;
import by.bstu.fit.savelev.busyday.models.Item;
import by.bstu.fit.savelev.busyday.models.Storage;
import by.bstu.fit.savelev.busyday.utils.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.bstu.fit.savelev.busyday.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<Item> activities;
    private DialogF dlg;
    private int index;
    ActivitiesAdapter mAdapter;
    SimpleAdapter adapter;
    RecyclerView rvActivities;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        activities = ((Storage) getContext().getApplicationContext()).getItems();
        dlg = new DialogF();

        fillListView(activities);
        registerForContextMenu(binding.booksList);
        rvActivities = binding.booksList;

        // Initialize contacts
        // Create adapter passing in the sample user data
        mAdapter = new ActivitiesAdapter(activities);
        // Attach the adapter to the recyclerview to populate items
        rvActivities.setAdapter(mAdapter);
        // Set layout manager to position the items
        rvActivities.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondFragment fragment = new SecondFragment();

                if (Orientation.isHorizontalOrientation(getActivity())) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.second_fragment_content_main, fragment)
                            .addToBackStack(null)
                            .commit();

                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment_content_main, fragment)
                            .addToBackStack(null)
                            .commit();

                }
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
        int position = -1;
        try {
            position = ((ActivitiesAdapter)binding.booksList.getAdapter()).getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.view:
                viewItem(position);
                return true;
            case R.id.edit:
                editItem(position); // метод, выполняющий действие при редактировании пункта меню
                return true;
            case R.id.delete:
                deleteItem(position); //метод, выполняющий действие при удалении пункта меню
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
public void SwitchView(){
    getActivity().supportInvalidateOptionsMenu();
    boolean isSwitched = mAdapter.toggleItemViewType();
    rvActivities.setLayoutManager(isSwitched ? new LinearLayoutManager(getActivity()) : new GridLayoutManager(getActivity(), 2));
    mAdapter.notifyDataSetChanged();

}
    private void editItem(int position) {
        SecondFragment fragment = new SecondFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("CurrentItem", position);  // Key, value
        fragment.setArguments(bundle);

        if (Orientation.isHorizontalOrientation(getActivity())) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.second_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commit();

        } else {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commit();

        }

    }
    private void deleteItem(int position) {
        dlg.show(getActivity().getSupportFragmentManager(),"dlg");
        index = position;
    }
    public void delete(){

        // Определение 'where' части запроса
        String selection = DBContract.DBEntry._ID + " = ?";
        // Определение аргументов в placeholder
        String[] selectionArgs = { Long.toString(activities.get(index).getId()) };
        // SQL statement.
        Storage.repository.Delete(selection, selectionArgs);
        activities.remove(index);
        ((ActivitiesAdapter)binding.booksList.getAdapter()).notifyDataSetChanged();
        //fillListView(activities);
    }

    private void viewItem(int position) {
        ViewInfo fragment = new ViewInfo();
        Bundle bundle = new Bundle();
        bundle.putParcelable("CurrentItem", (Parcelable) activities.get(position));  // Key, value
        fragment.setArguments(bundle);

        if (Orientation.isHorizontalOrientation(getActivity())) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.second_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commit();

        } else {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commit();

        }

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public SimpleAdapter fillListView(ArrayList<Item> activities) {
//        String[] projection = {
//                BaseColumns._ID,
//                DBContract.DBEntry.COLUMN_NAME_NAME,
//                DBContract.DBEntry.COLUMN_NAME_CATEGORY,
//        };
//        CursorAdapter listAdapter = new SimpleCursorAdapter(getContext(),
//                android.R.layout.simple_list_item_1,
//                Storage.repository.GetCursor(projection, null, null, null, null,null),
//                new String[]{DBContract.DBEntry.COLUMN_NAME_NAME},
//        new int[]{android.R.id.text1},
//                0);
//        binding.booksList.setAdapter(listAdapter);

//        binding.booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
//                                    long id) {
//                viewItem(position);
//
//            }
//        });
        binding.booksList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), binding.booksList,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        viewItem(position);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return adapter;
    }


}