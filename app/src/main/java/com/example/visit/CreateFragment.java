package com.example.visit;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visit.Cache.CacheManager;
import com.example.visit.list.additionalInf.Contacts;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import com.jackandphantom.circularimageview.RoundedImage;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class CreateFragment extends Fragment {

    View rootView;
    Button btnSave, btnContact;
    BottomSheetBehavior bottomSheetBehavior;
    TextInputLayout name, post,address;
    CacheManager cacheManager;
    String currentImage = null;
    RoundedImage avatar;
    Contacts contacts;
    FrameLayout darkBack;
    private final int PICK_IMAGE = 1;

    CreateFragment(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create,container,false);
        rootView.setVisibility(View.GONE);

        initialization();

        return rootView;
    }

    private void initialization()
    {

        //Инициализация объектов UI
        name  = rootView.findViewById(R.id.nameCreate);
        post = rootView.findViewById(R.id.postCreate);
        address = rootView.findViewById(R.id.emailShow);
        avatar = rootView.findViewById(R.id.add_photo_view);
        bottomSheetBehavior = BottomSheetBehavior.from(rootView.findViewById(R.id.bottom_sheet));
        darkBack = rootView.findViewById(R.id.forDarkBack);
        darkBack.setAlpha(0.5f);
        darkBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        darkBack.setVisibility(View.GONE);

        //Инициализация additionalInf
        contacts = new Contacts();
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.bottom_sheet, contacts)
                .commit();

        btnSave = rootView.findViewById(R.id.save_button);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });


        btnContact = rootView.findViewById(R.id.btn_contact);
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkBack.setVisibility(View.VISIBLE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if(slideOffset == 0)
                    darkBack.setVisibility(View.GONE);

            }
        });

    }

    //Добавить проверку на поля
    private void save()
    {
        if(!contacts.check()) {
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.exceptionContact),Toast.LENGTH_LONG).show();
            return;
        }

        Person person = new Person(
                name.getEditText().getText().toString(),
                post.getEditText().getText().toString(),
                currentImage,
                contacts.getEmail(),
                contacts.getVK(),
                contacts.getNumber(),
                contacts.getDiscord());

        cacheManager.addPerson(person);
    }

    public void Gone()
    {
        rootView.setVisibility(View.GONE);
    }

    public void Visible()
    {
        rootView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri uri = imageReturnedIntent.getData();
                    currentImage = String.valueOf(uri);

                    //Грузим фотку
                    ContentResolver cr = getContext().getContentResolver();
                    try {
                        avatar.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Ошибка загрузки", e);
                    }
                }
        }}
}
