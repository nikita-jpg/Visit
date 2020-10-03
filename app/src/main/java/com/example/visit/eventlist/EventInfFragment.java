package com.example.visit.eventlist;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.CheckInputInf;
import com.example.visit.R;
import com.example.visit.TeamEvent;
import com.example.visit.сache.CacheManager;
import com.google.android.material.textfield.TextInputLayout;
import com.jackandphantom.circularimageview.RoundedImage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.example.visit.createperson.Contacts.check;

public class EventInfFragment extends androidx.fragment.app.DialogFragment {
    private CacheManager cacheManager;
    private RVAdapterTeam rvAdapterTeam;
    private Button buttonEdit;
    private ImageButton buttonNextPerson, buttonPrevPerson;
    private Uri uri;
    private List<TeamEvent> teamEvents;
    private TeamEvent teamEvent;
    private Context context;
    private RoundedImage imgFir, imgSec;
    private TextInputLayout title,desc1,desc2;
    private String curImg1 = "";
    private String curImg2 = "";
    private boolean clickable = false;
    private final int PICK_IMAGE_1 = 1;
    private final int PICK_IMAGE_2 = 2;
    private int position;

    public EventInfFragment(CacheManager cacheManager, TeamEvent teamEvent, Context context, RVAdapterTeam rvAdapterTeam) {
        this.cacheManager = cacheManager;
        this.teamEvent = teamEvent;
        this.context = context;
        this.rvAdapterTeam = rvAdapterTeam;
    }

    public void setTeamEvents(List<TeamEvent> teamEvents) {
        this.teamEvents = teamEvents;
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

        final EventInfFragment thisEventInfFragment = this;

        title.getEditText().setText(teamEvent.getTitle());
        desc1.getEditText().setText(teamEvent.getDesc1());
        desc2.getEditText().setText(teamEvent.getDesc2());

        buttonEdit = v.findViewById(R.id.event_inf_btn);
        buttonNextPerson = v.findViewById(R.id.nextPerson);
        buttonPrevPerson = v.findViewById(R.id.prevPerson);

        position = teamEvents.indexOf(teamEvent);

        buttonNextPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position + 1 >= teamEvents.size()) {
                    position = -1;
                }
                EventInfFragment nextEventInfFragment = new EventInfFragment(cacheManager, teamEvents.get(position + 1), context, rvAdapterTeam);
                nextEventInfFragment.setTeamEvents(teamEvents);
                nextEventInfFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "addDialog");
                thisEventInfFragment.dismiss();
            }
        });

        buttonPrevPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position - 1 < 0) {
                    position = teamEvents.size();
                }

                EventInfFragment nextEventInfFragment = new EventInfFragment(cacheManager, teamEvents.get(position - 1), context, rvAdapterTeam);
                nextEventInfFragment.setTeamEvents(teamEvents);
                nextEventInfFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "addDialog");
                thisEventInfFragment.dismiss();
            }
        });

        //Грузим фотку
        ContentResolver cr = context.getContentResolver();
        try {
            uri = Uri.parse(teamEvent.getImg1());
            curImg1 = String.valueOf(uri);
            imgFir.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));

            uri = Uri.parse(teamEvent.getImg2());
            curImg2 = String.valueOf(uri);
            imgSec.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
        } catch (IOException e) {
            Toast.makeText(context, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
        }

        imgFir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE_1);
            }
        });

        imgSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE_2);
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

        offClickable();
        return v;
    }

    private void edit() {
        teamEvent.setTitle(title.getEditText().getText().toString());
        teamEvent.setDesc1(desc1.getEditText().getText().toString());
        teamEvent.setDesc2(desc2.getEditText().getText().toString());
        teamEvent.setImg1(curImg1);
        teamEvent.setImg2(curImg2);
        cacheManager.teamEdit(teamEvent);
        rvAdapterTeam.notifyDataSetChanged();
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
                .TRANSPARENT));
        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case PICK_IMAGE_1:
                if (resultCode == RESULT_OK) {
                    Uri uri = imageReturnedIntent.getData();
                    curImg1 = String.valueOf(uri);

                    //Грузим фотку
                    ContentResolver cr = getContext().getContentResolver();
                    try {
                        imgFir.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr, uri));
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Ошибка загрузки", e);
                    }
                }
                break;
            case PICK_IMAGE_2:
                if (resultCode == RESULT_OK) {
                    Uri uri = imageReturnedIntent.getData();
                    curImg2 = String.valueOf(uri);

                    //Грузим фотку
                    ContentResolver cr = getContext().getContentResolver();
                    try {
                        imgSec.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr, uri));
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Ошибка загрузки", e);
                    }
                }
                break;
        }
    }
}
