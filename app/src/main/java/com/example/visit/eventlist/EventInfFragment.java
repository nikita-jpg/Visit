package com.example.visit.eventlist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.visit.CheckInputInf;
import com.example.visit.R;
import com.example.visit.TeamEvent;
import com.example.visit.сache.CacheManager;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.HashMap;

import static android.content.ContentValues.TAG;
import static com.example.visit.createperson.Contacts.check;

public class EventInfFragment extends androidx.fragment.app.DialogFragment {
    private CacheManager cacheManager;
    private Button buttonEdit;
    private Uri uri;
    private TeamEvent teamEvent;
    private Context context;
    private ImageView imgFir, imgSec;
    private TextInputLayout title,desc1,desc2;
    private String curImg1 = "";
    private String curImg2 = "";
    private boolean clickable = false;
    private final int PICK_IMAGE = 1;

    public EventInfFragment(CacheManager cacheManager, TeamEvent teamEvent, Context context) {
        this.cacheManager = cacheManager;
        this.teamEvent = teamEvent;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_inf_dialog, container, false);
        title = v.findViewById(R.id.event_inf_title);
        imgFir = v.findViewById(R.id.event_inf_photo_1);
        imgSec = v.findViewById(R.id.event_inf_photo_2);
        desc1 = v.findViewById(R.id.event_inf_edit_1);
        desc2 = v.findViewById(R.id.event_inf_edit_2);

        title.getEditText().setText(teamEvent.getTitle());
        desc1.getEditText().setText(teamEvent.getDesc1());
        desc2.getEditText().setText(teamEvent.getDesc2());

        buttonEdit = v.findViewById(R.id.event_inf_btn);

        //Грузим фотку
        ContentResolver cr = context.getContentResolver();
        try {
            uri = Uri.parse(teamEvent.getImg1());
            imgFir.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
            uri = Uri.parse(teamEvent.getImg2());
            imgSec.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
        } catch (IOException e) {
            Toast.makeText(context, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
        }

        imgFir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });

        imgSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickable) {
                    edit();
                    offClickable();
                    clickable = false;
                    buttonEdit.setText("Редактировать");
                }
                else {
                    onClickable();
                    buttonEdit.setText("Сохранить");
                    clickable = true;
                }
            }
        });

        return v;
    }

    private void edit() {
        teamEvent.setTitle(title.getEditText().getText().toString());
        teamEvent.setDesc1(desc1.getEditText().getText().toString());
        teamEvent.setDesc2(desc2.getEditText().getText().toString());
        teamEvent.setImg1(curImg1);
        teamEvent.setImg1(curImg2);
        cacheManager.teamEdit(teamEvent);
        Toast.makeText(getActivity().getApplicationContext(),getString(R.string.saved),Toast.LENGTH_LONG).show();
    }

    private void onClickable()
    {
        imgFir.setFocusable(true);
        imgFir.setClickable(true);

        imgSec.setFocusable(true);
        imgSec.setClickable(true);

        title.getEditText().setFocusableInTouchMode(true);
        desc1.getEditText().setFocusableInTouchMode(true);
        desc2.getEditText().setFocusableInTouchMode(true);

    }

    private void offClickable()
    {
        imgFir.setFocusable(false);
        imgFir.setClickable(false);

        imgSec.setFocusable(false);
        imgSec.setClickable(false);

        title.getEditText().setFocusableInTouchMode(false);
        desc1.getEditText().setFocusableInTouchMode(false);
        desc2.getEditText().setFocusableInTouchMode(false);

    }

    public EventInfFragment(){}

    public TeamEvent getTeamEvent() {
        return teamEvent;
    }

    public void setTeamEvent(TeamEvent teamEvent) {
        this.teamEvent = teamEvent;
    }
}
