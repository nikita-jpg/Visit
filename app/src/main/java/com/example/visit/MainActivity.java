package com.example.visit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.visit.Cache.CacheManager;
import com.example.visit.list.ListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CacheManager cacheManager = new CacheManager(getApplicationContext());


        final ListFragment listFragment = new ListFragment(cacheManager);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, listFragment)
                .commit();

        final CreateFragment createFragment = new CreateFragment(cacheManager);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, createFragment)
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_menu)
                {
                    listFragment.Visible();
                    createFragment.Gone();
                }

                else
                {
                    listFragment.Gone();
                    createFragment.Visible();
                }
                return true;
            }
        });
    }
}