package com.example.visit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.visit.createperson.CreateFragment;
import com.example.visit.сache.CacheManager;
import com.example.visit.personlist.PersonListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Test test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        test = new Test(this,this,R.menu.test);
        LinearLayout linearLayout = findViewById(R.id.test_liner);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(test, params);

        test.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getTitle().toString().equals(getString(R.string.test_2)))
                {

                }
                return true;
            }
        });

        /*
        CacheManager cacheManager = new CacheManager(getApplicationContext());


        final PersonListFragment personListFragment = new PersonListFragment(cacheManager);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, personListFragment)
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
                if (item.getItemId() == R.id.action_menu) {
                    personListFragment.Visible();
                    createFragment.Gone();
                } else {
                    personListFragment.Gone();
                    createFragment.Visible();
                }
                return true;
            }
        });

         */
    }

    public void setFragment(int id)
    {
        Log.i("Test","setFragment work, id:"+id);
    }


}