package by.bstu.fit.savelev.busyday;

import static by.bstu.fit.savelev.busyday.utils.JsonUtil.DeserializeDataFromJson;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.view.View;

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
import by.bstu.fit.savelev.busyday.models.Item;
import by.bstu.fit.savelev.busyday.models.Storage;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ArrayList<Item> activities;
    public ArrayList<HashMap<String, String>> hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(DeserializeDataFromJson(this)){
            activities = ((Storage) this.getApplicationContext()).getItems();

        }
        else
            activities = new ArrayList<>();

        activities = ((Storage) this.getApplicationContext()).getItems();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                break;
            case R.id.action_count:
                countByCategories();
                break;
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

    private void countByCategories(){
        CalculatedData fragment = new CalculatedData();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}