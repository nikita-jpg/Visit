package com.example.visit.eventlist;

import android.content.ContentResolver;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.R;
import com.example.visit.TeamEvent;
import com.example.visit.personlist.PersonalInfFragment;
import com.example.visit.сache.CacheManager;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class RVAdapterTeam extends RecyclerView.Adapter<RVAdapterTeam.TeamViewHolder> {

    List<TeamEvent> teamEvents;
    Context context;
    CacheManager cacheManager;

    public RVAdapterTeam(List<TeamEvent> teamEvents, Context context, CacheManager cacheManager) {
        this.teamEvents = teamEvents;
        this.context = context;
        this.cacheManager = cacheManager;
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        ImageView firstPhoto;
        Button show;
        TeamViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.event_desc_1);
            firstPhoto = itemView.findViewById(R.id.photoAvatar);
            show = itemView.findViewById(R.id.show);
        }
    }


    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        RVAdapterTeam.TeamViewHolder teamViewHolder = new RVAdapterTeam.TeamViewHolder(view);
        return teamViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TeamViewHolder holder, final int position) {
        holder.title.setText(teamEvents.get(position).getTitle());

        //Кнопка "Посмотреть"
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventInfFragment eventInfFragment = new EventInfFragment(cacheManager,teamEvents.get(position),context,RVAdapterTeam.this);
                eventInfFragment.setTeamEvent(teamEvents.get(position));
                eventInfFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "addDialog");
            }
        });

        //Грузим фотку
        ContentResolver cr = context.getContentResolver();
        try {
            Uri uri = Uri.parse(teamEvents.get(position).getImg1());
            holder.firstPhoto.setImageBitmap(android.provider.MediaStore.Images.Media.getBitmap(cr,uri ));
        } catch (IOException e) {
            Toast.makeText(context, context.getString(R.string.expLoadImage), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return teamEvents.size();
    }

}
