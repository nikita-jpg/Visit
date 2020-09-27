package com.example.visit.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class AddDialogFragment extends androidx.fragment.app.DialogFragment {
    private Button buttonSave,buttonDelete,buttonSetPhoto;
    private EditText zametkaName;
    private EditText zametkaValue;
    private final int Pick_image = 1;
    private Context context;

    AddDialogFragment(Context context)
    {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        return null;
    }
}
