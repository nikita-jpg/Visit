package com.example.visit.list;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.visit.Person;
import com.example.visit.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class AddDialogFragment extends androidx.fragment.app.DialogFragment {
    private Button buttonSave,buttonDelete,buttonSetPhoto;
    private TextInputLayout name;
    private TextInputLayout number;
    private TextInputLayout email;
    private ImageView avatar;
    private Context context;
    private Person person;

    AddDialogFragment(Context context,Person person)
    {
        this.context = context;
        this.person = person;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_show, container, false);
        name = v.findViewById(R.id.nameShow);
        number = v.findViewById(R.id.nameShow);
        email = v.findViewById(R.id.emailShow);
        avatar = v.findViewById(R.id.avatarShow);

        name.getEditText().setText(person.getName());
        number.getEditText().setText(person.getNumber());
        email.getEditText().setText(person.getEmail());

        //Грузим фотку
        ContentResolver cr = context.getContentResolver();
        try {
            Uri uri = Uri.parse(person.getPhotoId());
            avatar.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
        } catch (IOException e) {
            Toast.makeText(context, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Ошибка загрузки", e);
        }

        return v;
    }
}
