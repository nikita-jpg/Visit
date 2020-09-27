package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visit.Cache.CacheManager;
import com.google.android.material.textfield.TextInputLayout;


public class CreateFragment extends Fragment {

    View rootView;
    CacheManager cacheManager;

    CreateFragment(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create,container,false);
        rootView.setVisibility(View.GONE);
        Button saveBtn = rootView.findViewById(R.id.save_button);

        final TextInputLayout name = rootView.findViewById(R.id.name);
        final TextInputLayout number = rootView.findViewById(R.id.number);
        final TextInputLayout address = rootView.findViewById(R.id.address);
        name.getEditText().getText().toString();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getEditText().getText().toString();
                String addressStr = address.getEditText().getText().toString();
                String numberStr = number.getEditText().getText().toString();
                Person person = new Person(nameStr,addressStr,numberStr);

                cacheManager.addPerson(person);
            }
        });
        return rootView;
    }

    public void Gone()
    {
        rootView.setVisibility(View.GONE);
    }

    public void Visible()
    {
        rootView.setVisibility(View.VISIBLE);
    }
}
