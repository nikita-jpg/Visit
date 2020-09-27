package com.example.visit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        final View menuFragment = findViewById(R.id.listFragment);
        final View createFragment = findViewById(R.id.createFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_menu)
                {
                    //item.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBlue)));
                    menuFragment.setVisibility(View.VISIBLE);
                    createFragment.setVisibility(View.GONE);
                }

                else
                {
                    menuFragment.setVisibility(View.GONE);
                    createFragment.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }
}