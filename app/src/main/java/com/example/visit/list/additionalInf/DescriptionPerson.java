package com.example.visit.list.additionalInf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visit.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class DescriptionPerson extends Fragment {
    private BottomSheetBehavior bottomSheetBehavior;
    private View rootView;

    public DescriptionPerson(BottomSheetBehavior bottomSheetBehavior)
    {
        this.bottomSheetBehavior = bottomSheetBehavior;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contact,container,false);
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
