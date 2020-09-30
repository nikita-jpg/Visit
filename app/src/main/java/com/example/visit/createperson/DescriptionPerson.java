package com.example.visit.createperson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visit.R;
import com.example.visit.CheckInputInf;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class DescriptionPerson extends Fragment {
    private BottomSheetBehavior bottomSheetBehavior;
    private View rootView;
    private TextInputLayout descriptionInputLayout;
    private HashMap<String,String> map = new HashMap<>();
    private Button btnSave;
    private CheckInputInf checkInputInf;

    public DescriptionPerson(BottomSheetBehavior bottomSheetBehavior)
    {
        this.bottomSheetBehavior = bottomSheetBehavior;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_description_person,container,false);
        descriptionInputLayout = rootView.findViewById(R.id.description_field);
        btnSave = rootView.findViewById(R.id.save_description_button);
        checkInputInf = new CheckInputInf(getContext());
        map.put("description", "");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputInf.checkDescription(map, descriptionInputLayout)) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Toast.makeText(getContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
                }
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

    public String getDescription() {
        return map.get("description");
    }
}
