package com.example.gamershub.objectPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamershub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomHomeAdapterClass extends RecyclerView.Adapter<CustomHomeAdapterClass.customAdap> {

    private ArrayList<gameHome> games;
    private Context context;


    public CustomHomeAdapterClass(@NonNull ArrayList<gameHome> games, Context context){
        this.games = games;
        this.context = context;
    }



    @NonNull
    @Override
    public customAdap onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homeitemlayout,viewGroup,false);
        final customAdap customAdap = new customAdap(view);



        return customAdap;
    }

    @Override
    public void onBindViewHolder(@NonNull customAdap viewHolder, int i) {
        gameHome game = games.get(i);
        viewHolder.name.setText(game.getName());
        //Picasso.get().load(game.getImageViewUrl()).into(viewHolder.url);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class customAdap extends RecyclerView.ViewHolder{
        protected TextView name;
        protected ImageView url;

        public customAdap(View view){
            super(view);
            this.name = view.findViewById(R.id.gameTitle);
            this.url = view.findViewById(R.id.gameImage);
        }
    }

}
