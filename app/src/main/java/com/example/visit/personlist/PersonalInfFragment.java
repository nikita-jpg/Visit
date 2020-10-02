package com.example.visit.personlist;

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

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.visit.CheckInputInf;
import com.example.visit.Person;
import com.example.visit.R;
import com.example.visit.сache.CacheManager;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.example.visit.createperson.Contacts.check;

public class PersonalInfFragment extends DialogFragment {
    private CacheManager cacheManager;
    private List<Person> people;
    private Button buttonEdit;
    private int[] fieldsIDs = {R.id.nameShow, R.id.professionShow, R.id.numberShow, R.id.emailShow,
            R.id.vkShow, R.id.discordShow, R.id.gitShow, R.id.descriptionShow};
    private TextInputLayout[] fields = new TextInputLayout[fieldsIDs.length];
    private ImageView avatar;
    private Context context;
    private Person person;
    private Uri uri;
    private String currentImage;
    private boolean clickable = false;
    private final int PICK_IMAGE = 1;

    PersonalInfFragment(Context context, Person person, CacheManager cacheManager)
    {
        this.context = context;
        this.person = person;
        this.cacheManager = cacheManager;
    }

    public PersonalInfFragment() {
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_show, container, false);

        for (int i = 0; i < fields.length; i++) {
            fields[i] = v.findViewById(fieldsIDs[i]);
        }
        fields[0].getEditText().setText(person.getName());
        fields[1].getEditText().setText(person.getPost());
        fields[2].getEditText().setText(person.getNumber());
        fields[3].getEditText().setText(person.getEmail());
        fields[4].getEditText().setText(person.getVk());
        fields[5].getEditText().setText(person.getDiscord());
        fields[6].getEditText().setText(person.getGit());
        fields[7].getEditText().setText(person.getDescription());

        buttonEdit = v.findViewById(R.id.editBtnShow);
        avatar = v.findViewById(R.id.avatarShow);

        //Грузим фотку
        ContentResolver cr = context.getContentResolver();
        try {
            uri = Uri.parse(person.getPhotoId());
            avatar.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
        } catch (IOException e) {
            Toast.makeText(context, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Ошибка загрузки", e);
        }

        currentImage = String.valueOf(uri);

        avatar.setOnClickListener(new View.OnClickListener() {
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
                    if (edit()) {
                        offClickable();
                        clickable = false;
                        buttonEdit.setText("Редактировать");
                    }
                }
                else {
                    onClickable();
                    buttonEdit.setText("Сохранить");
                    clickable = true;
                }
            }
        });

        //По умолчанию поля недоступны для редактирования
        offClickable();

        return v;
    }

    private boolean edit() {
        HashMap<String,String> map = new HashMap<>();
        map.put("vkId","");
        map.put("email","");
        map.put("discord","");
        map.put("number","");
        map.put("git","");
        map.put("description", "");

        CheckInputInf checkInputInf = new CheckInputInf(getContext());

        if (checkInputInf.checkContact(map, fields[4], fields[2], fields[5], fields[3], fields[6])
                && checkInputInf.checkDescription(map, fields[7])
                && checkInputInf.checkNameProfAvat(
                        fields[0].getEditText().getText().toString(),
                        fields[1].getEditText().getText().toString(),
                        String.valueOf(uri))
                && check(map)) {
            person.setName(getText(fields[0]));
            person.setPost(getText(fields[1]));
            person.setNumber(getText(fields[2]));
            person.setEmail(getText(fields[3]));
            person.setVk(getText(fields[4]));
            person.setDiscord(getText(fields[5]));
            person.setGit(getText(fields[6]));
            person.setDescription(getText(fields[7]));
            person.setPhotoId(currentImage);

            cacheManager.personEdit(person);
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.saved),Toast.LENGTH_LONG).show();

            return true;
        }
        return false;
    }

    private String getText(TextInputLayout field) {
        return field.getEditText().getText().toString();
    }

    private void onClickable()
    {
        avatar.setFocusable(true);
        avatar.setClickable(true);

        for (int i = 0; i < fields.length; i++) {
            fields[i].getEditText().setFocusableInTouchMode(true);
        }
    }

    private void offClickable()
    {
        avatar.setFocusable(false);
        avatar.setClickable(false);

        for (int i = 0; i < fields.length; i++) {
            fields[i].getEditText().setFocusable(false);
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
