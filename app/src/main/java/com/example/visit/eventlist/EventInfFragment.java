package com.example.visit.eventlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
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
    private ImageButton buttonNextEvent, buttonPrevEvent;
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
    private Button cancel,buttonEdit,delete;

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
        cancel = v.findViewById(R.id.event_inf_btn_cancel);
        delete = v.findViewById(R.id.event_inf_btn_delete);

        final EventInfFragment thisEventInfFragment = this;

        title.getEditText().setText(teamEvent.getTitle());
        desc1.getEditText().setText(teamEvent.getDesc1());
        desc2.getEditText().setText(teamEvent.getDesc2());

        buttonEdit = v.findViewById(R.id.event_inf_btn_edit);
        buttonNextEvent = v.findViewById(R.id.nextEvent);
        buttonPrevEvent = v.findViewById(R.id.prevEvent);

        position = teamEvents.indexOf(teamEvent);

        buttonNextEvent.setOnClickListener(new View.OnClickListener() {
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

        buttonPrevEvent.setOnClickListener(new View.OnClickListener() {
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clickable) {
                    dismiss();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
                builder.setCancelable(true);
                builder.setTitle(R.string.exitWithoutSave);
                builder.setMessage(R.string.exitWithoutSaveMess);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // Кнопка ОК
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
                builder.setMessage(R.string.exitAfterDeletePers);

                DialogInterface.OnClickListener positiveClickListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        teamEvents.remove(teamEvent);
                        cacheManager.teamDelete(teamEvent);
                        rvAdapterTeam.notifyDataSetChanged();
                        dismiss();
                    }
                };

                builder.setPositiveButton(R.string.yes, positiveClickListener);
                builder.setNegativeButton(R.string.no, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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

        title.getEditText().setFocusable(false);
        desc1.getEditText().setFocusable(false);
        desc2.getEditText().setFocusable(false);

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
