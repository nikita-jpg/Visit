package com.example.visit.createperson;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visit.CheckInputInf;
import com.example.visit.MainActivity;
import com.example.visit.Person;
import com.example.visit.R;
import com.example.visit.eventlist.RVAdapterTeam;
import com.example.visit.personlist.RVAdapterPerson;
import com.example.visit.сache.CacheManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import com.jackandphantom.circularimageview.RoundedImage;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class CreateFragment extends Fragment {

    View rootView;
    MainActivity mainActivity;
    Button btnSave, btnContact, btnDescription;
    BottomSheetBehavior bottomSheetBehavior;
    TextInputLayout name, post, address;
    CacheManager cacheManager;
    String currentImage = null;
    RoundedImage avatar;
    Contacts contacts;
    DescriptionPerson description;
    CheckInputInf checkInputInf;
    FrameLayout darkBack;
    private final int PICK_IMAGE = 1;

    public CreateFragment(CacheManager cacheManager, MainActivity mainActivity) {
        this.cacheManager = cacheManager;
        this.mainActivity = mainActivity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create, container, false);
        rootView.setVisibility(View.GONE);

        init();

        return rootView;
    }

    private void init() {
        //Инициализация объекта для проверки введённых данных
        checkInputInf = new CheckInputInf(getContext());

        //Аватар по умолчанию
        Resources resources = getContext().getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.default_avatar) + '/' + resources.getResourceTypeName(R.drawable.default_avatar) + '/' + resources.getResourceEntryName(R.drawable.default_avatar));
        currentImage = String.valueOf(uri);

        //Инициализация объектов UI
        bottomSheetBehavior = BottomSheetBehavior.from(rootView.findViewById(R.id.bottom_sheet));
        name  = rootView.findViewById(R.id.event_desc_1);
        post = rootView.findViewById(R.id.postCreate);
        address = rootView.findViewById(R.id.emailShow);
        avatar = rootView.findViewById(R.id.add_photo_view);
        darkBack = rootView.findViewById(R.id.forDarkBack);
        darkBack.setAlpha(0.5f);
        darkBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stateOfSheet = bottomSheetBehavior.getState();
                if (stateOfSheet == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        darkBack.setVisibility(View.GONE);


        //Инициализация Contact
        contacts = new Contacts(bottomSheetBehavior);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.bottom_sheet, contacts)
                .commit();
        btnContact = rootView.findViewById(R.id.btn_contact);
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                darkBack.setVisibility(View.VISIBLE);
                description.Gone();
                contacts.Visible();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        //Инициализация Description
        description = new DescriptionPerson(bottomSheetBehavior);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.bottom_sheet, description)
                .commit();


        //Кнопка сохранения
        btnSave = rootView.findViewById(R.id.save_button);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        //Аватарка
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });

        //Инициализация описания
        btnDescription = rootView.findViewById(R.id.btn_describe);
        btnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                darkBack.setVisibility(View.VISIBLE);
                description.Visible();
                contacts.Gone();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset == 0) {
                    darkBack.setVisibility(View.GONE);
                    hideKeyboard();
                }

            }
        });

    }

    private void save() {
        checkInputInf.checkNameProfAvat(name.getEditText().getText().toString(), post.getEditText().getText().toString(), currentImage);

        if (!Contacts.check(contacts.getMap())) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.exceptionContact), Toast.LENGTH_LONG).show();
            return;
        }

        Person person = new Person(
                name.getEditText().getText().toString(),
                post.getEditText().getText().toString(),
                currentImage,
                contacts.getEmail(),
                contacts.getVKId(),
                contacts.getNumber(),
                contacts.getDiscord(),
                contacts.getGit(),
                description.getDescription());

        cacheManager.personAdd(person);
        mainActivity.update(person);
        Toast.makeText(getActivity().getApplicationContext(),getString(R.string.saved),Toast.LENGTH_LONG).show();
    }

    public void Gone() {
        rootView.setVisibility(View.GONE);
    }

    public void Visible() {
        rootView.setVisibility(View.VISIBLE);
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = getActivity().getCurrentFocus();
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri uri = imageReturnedIntent.getData();
                    currentImage = String.valueOf(uri);

                    //Грузим фотку
                    ContentResolver cr = getContext().getContentResolver();
                    try {
                        avatar.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr, uri));
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Ошибка загрузки", e);
                    }
                }
        }
    }
}