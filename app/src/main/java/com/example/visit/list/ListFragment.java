package com.example.visit.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.Cache.CacheManager;
import com.example.visit.Person;
import com.example.visit.R;
import com.example.visit.list.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    RecyclerView rootView;
    CacheManager cacheManager;

    public ListFragment(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (RecyclerView) inflater.inflate(R.layout.fragment_menu,container,false);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2);
        linearLayoutManager.setInitialPrefetchItemCount(2);
        rootView.setLayoutManager(linearLayoutManager);

        List<Person> persons = cacheManager.getAllText();;
        RVAdapter rvAdapter = new RVAdapter(persons,getContext());
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