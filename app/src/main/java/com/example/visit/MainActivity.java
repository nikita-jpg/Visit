package com.example.visit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.visit.createperson.CreateFragment;
import com.example.visit.сache.CacheManager;
import com.example.visit.personlist.PersonListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private CustomButNavBar customButNavBar;
    private CacheManager cacheManager;
    private PersonListFragment personListFragment;
    private CreateFragment createFragment;
    private RelativeLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainLayout = findViewById(R.id.container);
        cacheManager = new CacheManager(getApplicationContext());

        personListFragment = new PersonListFragment(cacheManager);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, personListFragment)
                .commit();

        createFragment = new CreateFragment(cacheManager);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, createFragment)
                .commit();

        CustomButNavBar bottomNavigationView = new CustomButNavBar(this,this,R.menu.menu_bottom_navigation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bottomNavigationView.setItemTextColor(getColorStateList(R.color.bnv_tab_item_foreground));
            bottomNavigationView.setItemIconTintList(getColorStateList(R.color.bnv_tab_item_foreground));
            bottomNavigationView.setBackgroundColor(getColor(R.color.colorBlack));
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mainLayout.addView(bottomNavigationView,layoutParams);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setFragment(item.getItemId());
                return true;
            }
        });


    }

    public void setFragment(int id)
    {
        if (id == R.id.action_menu) {
            personListFragment.Visible();
            createFragment.Gone();
        } else {
            personListFragment.Gone();
            createFragment.Visible();
        }
        Log.i("Test","setFragment work, id:"+id);
    }


}