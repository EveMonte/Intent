package by.bstu.fit.savelev.busyday;

import static by.bstu.fit.savelev.busyday.utils.JsonUtil.DeserializeDataFromJson;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.provider.BaseColumns;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import by.bstu.fit.savelev.busyday.databinding.ActivityMainBinding;
import by.bstu.fit.savelev.busyday.models.ActivityCategories;
import by.bstu.fit.savelev.busyday.models.DBContract;
import by.bstu.fit.savelev.busyday.models.Item;
import by.bstu.fit.savelev.busyday.models.Repository;
import by.bstu.fit.savelev.busyday.models.Storage;
import by.bstu.fit.savelev.busyday.utils.DbHelper;
import by.bstu.fit.savelev.busyday.utils.Orientation;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ArrayList activities;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public ArrayList<HashMap<String, String>> hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Storage.repository = new Repository(this);
        activities = Storage.repository.GetDirectoryList(null, null, null, null, null);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawerLayout = binding.myDrawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_order:
                sortItems();
                binding.myDrawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.action_count:
                countByCategories();
                binding.myDrawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.switch_view:
                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                if(fragments != null){
                    for(Fragment fragment : fragments) {
                        if (fragment != null && fragment.isVisible())
                            if (fragment instanceof FirstFragment) {
                                ((FirstFragment) fragment).SwitchView();
                            }
                    }
                }
                binding.myDrawerLayout.closeDrawer(GravityCompat.START);

        }
        return true;
    }
});
        // to make the Navigation drawer icon always appear on the action bar
        ((Storage) this.getApplicationContext()).setItems(activities);


//        setSupportActionBar(binding.toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        //binding.myDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setQueryHint("Введите название");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
//                List<Fragment> fragments = fragmentManager.getFragments();
//                if(fragments != null){
//                    for(Fragment fragment : fragments){
//                        if(fragment != null && fragment.isVisible())
//                            if(fragment instanceof FirstFragment){
//                                ((FirstFragment)fragment).adapter.getFilter().filter(s);
//                            }
//                    }
//                }
//
//                return false;
//            }
//        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_order:
                sortItems();
                binding.myDrawerLayout.closeDrawer(GravityCompat.END);

                break;
            case R.id.action_count:
                countByCategories();
                binding.myDrawerLayout.closeDrawer(GravityCompat.END);
                break;
            case R.id.switch_view:
                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                if(fragments != null){
                    for(Fragment fragment : fragments) {
                        if (fragment != null && fragment.isVisible())
                            if (fragment instanceof FirstFragment) {
                                ((FirstFragment) fragment).SwitchView();
                            }
                    }
                }
                binding.myDrawerLayout.closeDrawer(GravityCompat.END);

                break;
        }
        if(binding.myDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.myDrawerLayout.closeDrawer(GravityCompat.END);
        }else {
            binding.myDrawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortItems(){
        activities = Storage.sortByCategoryName(activities);
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    if(fragment instanceof FirstFragment){
                        ((FirstFragment)fragment).fillListView(activities);
                    }
            }
        }
    }

    private void countByCategories() {
        CalculatedData fragment = new CalculatedData();

        if (Orientation.isHorizontalOrientation(this)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.second_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commit();

        }

    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}