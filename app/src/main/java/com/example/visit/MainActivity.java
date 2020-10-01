package com.example.visit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.visit.createperson.CreateFragment;
import com.example.visit.createteamivent.CreateTeamEvent;
import com.example.visit.сache.CacheManager;
import com.example.visit.personlist.PersonListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //Фрагменты
    private PersonListFragment personListFragment;
    private CreateFragment createPersonFragment;
    private CreateTeamEvent createTeamEventFragment;

    private CacheManager cacheManager;
    private RelativeLayout mainLayout;
    private CustomButNavBar bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.container);
        cacheManager = new CacheManager(getApplicationContext());

        //Запускаем Фрагменты
        initFragments();

        //Запускаем navBar
        initNavBar();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mainLayout.addView(bottomNavigationView,layoutParams);

    }

    private void initNavBar()
    {
        bottomNavigationView = new CustomButNavBar(this,this,R.menu.menu_bottom_navigation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bottomNavigationView.setItemTextColor(getColorStateList(R.color.bnv_tab_item_foreground));
            bottomNavigationView.setItemIconTintList(getColorStateList(R.color.bnv_tab_item_foreground));
            bottomNavigationView.setBackgroundColor(getColor(R.color.colorBlack));
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setFragment(item.getTitle().toString());
                return true;
            }
        });
    }

    private void initFragments()
    {
        personListFragment = new PersonListFragment(cacheManager);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, personListFragment)
                .commit();

        createPersonFragment = new CreateFragment(cacheManager);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, createPersonFragment)
                .commit();

        createTeamEventFragment = new CreateTeamEvent();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,createTeamEventFragment)
                .commit();

    }

    public void setFragment(String title)
    {
        personListFragment.Gone();
        createPersonFragment.Gone();
        createTeamEventFragment.Gone();

        if(title.equals(getString(R.string.create_person)))
            createPersonFragment.Visible();

        if(title.equals(getString(R.string.create_team_event)))
            createTeamEventFragment.Visible();

        if(title.equals(getString(R.string.menu)))
            personListFragment.Visible();
    }


}