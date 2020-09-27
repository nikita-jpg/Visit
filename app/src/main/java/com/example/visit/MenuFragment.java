package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    RecyclerView rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (RecyclerView) inflater.inflate(R.layout.fragment_menu,container,false);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2);
        linearLayoutManager.setInitialPrefetchItemCount(2);
        rootView.setLayoutManager(linearLayoutManager);

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", 0));
        persons.add(new Person("Lavery Maiss", "25 years old", 0));
        persons.add(new Person("Lillie Watts", "35 years old", 0));
        RVAdapter rvAdapter = new RVAdapter(persons);
        rootView.setAdapter(rvAdapter);

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
