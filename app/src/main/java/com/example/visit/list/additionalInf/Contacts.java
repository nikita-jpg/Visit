package com.example.visit.list.additionalInf;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visit.R;
import com.example.visit.list.Check;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class Contacts extends Fragment {

    private TextInputLayout vkId,number,discord,email;
    private HashMap<String,String> map = new HashMap<>();
    private Button btnSave;
    private Check check;
    private BottomSheetBehavior bottomSheetBehavior;

    public Contacts(BottomSheetBehavior bottomSheetBehavior)
    {
        this.bottomSheetBehavior = bottomSheetBehavior;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact,container,false);
        vkId = rootView.findViewById(R.id.createVK);
        number = rootView.findViewById(R.id.createNumber);
        discord = rootView.findViewById(R.id.createDiscord);
        email = rootView.findViewById(R.id.createEmail);
        btnSave = rootView.findViewById(R.id.createBtn);

        check = new Check(getContext());

        map.put("vkId","");
        map.put("email","");
        map.put("discord","");
        map.put("number","");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check.checkContact(map, vkId,number,discord,email))
                {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Toast.makeText(getContext(),getString(R.string.saved),Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    public boolean check()
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

    public HashMap<String, String> getMap() {
        return map;
    }
}
