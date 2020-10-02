package com.example.visit.createteamivent;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visit.MainActivity;
import com.example.visit.R;
import com.example.visit.TeamEvent;
import com.example.visit.сache.CacheManager;
import com.google.android.material.textfield.TextInputLayout;
import com.jackandphantom.circularimageview.RoundedImage;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class CreateTeamEvent extends Fragment {

    private final int PICK_IMAGE_1 = 1;
    private final int PICK_IMAGE_2 = 2;

    private String titleStr,desc1Str,desc2Str,currentImage1,currentImage2;

    private View rootView;
    private Button btnSaveTeam;
    private TextInputLayout title,desc1,desc2;
    private RoundedImage img1,img2;

    private CacheManager cacheManager;
    private MainActivity mainActivity;

    public CreateTeamEvent(CacheManager cacheManager, MainActivity mainActivity)
    {
        this.cacheManager = cacheManager;
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_team_event,container,false);
        rootView.setVisibility(View.GONE);

        btnSaveTeam = rootView.findViewById(R.id.btn_save_team_event);
        title = rootView.findViewById(R.id.event_title);
        desc1 = rootView.findViewById(R.id.event_desc_1);
        desc2 = rootView.findViewById(R.id.event_desc_2);
        img1 = rootView.findViewById(R.id.event_photo_1);
        img2 = rootView.findViewById(R.id.event_photo_2);

        init();


        return rootView;
    }

    private void init()
    {
        titleStr = "";
        desc1Str = "";
        desc2Str = "";

        //Аватар по умолчанию
        Resources resources = getContext().getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.default_avatar) + '/' + resources.getResourceTypeName(R.drawable.default_avatar) + '/' + resources.getResourceEntryName(R.drawable.default_avatar));
        currentImage1 = String.valueOf(uri);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE_1);
            }
        });

        currentImage2 = String.valueOf(uri);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE_2);
            }
        });

        btnSaveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save()
    {
        titleStr = title.getEditText().getText().toString();
        if(titleStr.equals(""))
        {
            Toast.makeText(getContext(),getString(R.string.expTitle),Toast.LENGTH_LONG).show();
            return;
        }

        desc1Str = desc1.getEditText().getText().toString();
        desc2Str = desc2.getEditText().getText().toString();
        TeamEvent teamEvent = new TeamEvent(titleStr,desc1Str,desc2Str,currentImage1,currentImage2);
        cacheManager.teamAdd(teamEvent);
        mainActivity.update(teamEvent);
        Toast.makeText(getContext(),getString(R.string.saved),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case PICK_IMAGE_1:
                if(resultCode == RESULT_OK){
                    Uri uri = imageReturnedIntent.getData();
                    currentImage1 = String.valueOf(uri);

                    //Грузим фотку
                    ContentResolver cr = getContext().getContentResolver();
                    try {
                        img1.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Ошибка загрузки", e);
                    }
                }
                break;
            case PICK_IMAGE_2:
                if(resultCode == RESULT_OK){
                    Uri uri = imageReturnedIntent.getData();
                    currentImage2 = String.valueOf(uri);

                    //Грузим фотку
                    ContentResolver cr = getContext().getContentResolver();
                    try {
                        img2.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Ошибка загрузки", e);
                    }
                }
        }}

    public void Gone()
    {
        rootView.setVisibility(View.GONE);
    }

    public void Visible()
    {
        rootView.setVisibility(View.VISIBLE);
    }
}
