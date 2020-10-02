package com.example.visit.personlist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.Person;
import com.example.visit.R;
import com.example.visit.сache.CacheManager;

import java.io.IOException;
import java.util.List;

public class RVAdapterPerson extends RecyclerView.Adapter<RVAdapterPerson.PersonViewHolder>{
    List<Person> persons;
    Context context;
    CacheManager cacheManager;
    RVAdapterPerson(List<Person> persons, Context context, CacheManager cacheManager){
        this.persons = persons;
        this.context = context;
        this.cacheManager = cacheManager;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        ImageView personPhoto;
        Button show;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardView);
            personName = itemView.findViewById(R.id.event_desc_1);
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
                PersonalInfFragment personalInfFragment = new PersonalInfFragment(context,persons.get(position), cacheManager, position);
                personalInfFragment.setPersons(persons);
                personalInfFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "addDialog");
            }
        });

        //Грузим фотку
        ContentResolver cr = context.getContentResolver();
        try {
            Uri uri = Uri.parse(persons.get(position).getPhotoId());
            holder.personPhoto.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
        } catch (IOException e) {
            Toast.makeText(context, context.getString(R.string.expLoadImage), Toast.LENGTH_SHORT).show();
        }

    }

    public void add(Person person)
    {
        persons.add(person);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

}