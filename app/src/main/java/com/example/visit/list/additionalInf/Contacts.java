package com.example.visit.list.additionalInf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visit.Person;
import com.example.visit.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class Contacts extends Fragment {

    private TextInputLayout vk,number,discord,email;
    private HashMap<String,String> map = new HashMap<>();
    private Button btnSave;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact,container,false);
        vk = rootView.findViewById(R.id.createVK);
        number = rootView.findViewById(R.id.createNumber);
        discord = rootView.findViewById(R.id.createDiscord);
        email = rootView.findViewById(R.id.createEmail);
        btnSave = rootView.findViewById(R.id.createBtn);

        map.put("vk","");
        map.put("email","");
        map.put("discord","");
        map.put("number","");



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("vk",vk.getEditText().getText().toString());
                map.put("email",number.getEditText().getText().toString());
                map.put("discord",discord.getEditText().getText().toString());
                map.put("number",email.getEditText().getText().toString());
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

    public String getVK()
    {
        return map.get("vk");
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
