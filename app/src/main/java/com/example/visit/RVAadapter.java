package com.example.visit;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{
    List<Person> persons;
    RVAdapter(List<Person> persons){
        this.persons = persons;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        ImageView personPhoto;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardView);
            personName = (TextView)itemView.findViewById(R.id.name);
            personPhoto = (ImageView) itemView.findViewById(R.id.photoAvatar);
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
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.personPhoto.setBackgroundColor(Color.RED);
        holder.personName.setText(persons.get(position).name);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

}