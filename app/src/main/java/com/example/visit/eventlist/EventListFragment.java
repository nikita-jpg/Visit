package com.example.visit.eventlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.Person;
import com.example.visit.R;
import com.example.visit.TeamEvent;
import com.example.visit.сache.CacheManager;

import java.util.List;

public class EventListFragment extends Fragment {

    private RecyclerView rootView;
    private CacheManager cacheManager;
    private RVAdapterTeam rvAdapterPerson;


    public EventListFragment(CacheManager cacheManager)
    {
        this.cacheManager = cacheManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (RecyclerView) inflater.inflate(R.layout.fragment_menu_event,container,false);


        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2);
        linearLayoutManager.setInitialPrefetchItemCount(2);
        rootView.setLayoutManager(linearLayoutManager);

        List<TeamEvent> events = cacheManager.teamGetAllText();
        rvAdapterPerson = new RVAdapterTeam(events,getContext(), cacheManager);
        rootView.setAdapter(rvAdapterPerson);
        Gone();



        return rootView;
    }

    public void update(TeamEvent event)
    {
        rvAdapterPerson.add(event);
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
