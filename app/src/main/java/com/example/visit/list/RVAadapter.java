package com.example.visit.list;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.Person;
import com.example.visit.R;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{
    List<Person> persons;
    Context context;
    RVAdapter(List<Person> persons,Context context){
        this.persons = persons;
        this.context = context;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        ImageView personPhoto;
        Button show;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardView);
            personName = itemView.findViewById(R.id.nameCreate);
            personPhoto = itemView.findViewById(R.id.photoAvatar);
            show = itemView.findViewById(R.id.show);
        }
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        PersonViewHolder personViewHolder = new PersonViewHolder(view);
        return personViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonViewHolder holder, final int position) {
        holder.personName.setText(persons.get(position).getName());

        //Кнопка "Посмотреть"
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment addDialogFragment = new AddDialogFragment(context,persons.get(position));
                addDialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "addDialog");
            }
        });

        //Грузим фотку
        ContentResolver cr = context.getContentResolver();
        try {
            Uri uri = Uri.parse(persons.get(position).getPhotoId());
            holder.personPhoto.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
        } catch (IOException e) {
            Toast.makeText(context, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Ошибка загрузки", e);
        }

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

}