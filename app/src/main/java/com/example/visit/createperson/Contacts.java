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
import java.util.Map;

public class Contacts extends Fragment {

    private TextInputLayout[] fields = new TextInputLayout[5];
    private int[] fieldsIDs = { R.id.createVK, R.id.createNumber,
            R.id.createDiscord, R.id.createEmail, R.id.createGit};
    private HashMap<String,String> map = new HashMap<>();
    private Button btnSave;
    private CheckInputInf checkInputInf;
    private View rootView;
    private BottomSheetBehavior bottomSheetBehavior;

    public Contacts(BottomSheetBehavior bottomSheetBehavior)
    {
        this.bottomSheetBehavior = bottomSheetBehavior;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contact,container,false);

        for (int i = 0; i < fields.length; i++) {
            fields[i] = rootView.findViewById(fieldsIDs[i]);
        }

        btnSave = rootView.findViewById(R.id.createBtn);


        checkInputInf = new CheckInputInf(getContext());

        map.put("vkId","");
        map.put("email","");
        map.put("discord","");
        map.put("number","");
        map.put("git","");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputInf.checkContact(map, fields[0], fields[1], fields[2], fields[3], fields[4]))
                {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Toast.makeText(getContext(),getString(R.string.saved),Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    public static boolean check(HashMap<String, String> map)
    {
        for (Map.Entry entry: map.entrySet())
            if(entry.getValue().equals(""))
                return false;

        return true;
    }

    public String getVKId()
    {
        return map.get("vkId");
    }
    public String getEmail()
    {
        return map.get("email");
    }
    public String getDiscord()
    {
        return map.get("discord");
    }
    public String getNumber()
    {
        return map.get("number");
    }
    public String getGit() {
        return map.get("git");
    }

    public HashMap<String, String> getMap() {
        return map;
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