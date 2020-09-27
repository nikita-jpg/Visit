package com.example.visit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visit.Cache.CacheManager;
import com.google.android.material.textfield.TextInputLayout;

import static android.app.Activity.RESULT_OK;


public class CreateFragment extends Fragment {

    View rootView;
    Button saveBtn,addPhotoBtn;
    TextInputLayout name,number,address;
    CacheManager cacheManager;
    String currentImage = null;
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
        name  = rootView.findViewById(R.id.nameCreate);
        number = rootView.findViewById(R.id.numberCreate);
        address = rootView.findViewById(R.id.Create);

        saveBtn = rootView.findViewById(R.id.save_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getEditText().getText().toString();
                String addressStr = address.getEditText().getText().toString();
                String numberStr = number.getEditText().getText().toString();
                Person person = new Person(nameStr,addressStr,numberStr,currentImage);

                cacheManager.addPerson(person);
            }
        });

        addPhotoBtn = rootView.findViewById(R.id.addImage);
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_OPEN_DOCUMENT:
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });
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
                }
        }}
}
