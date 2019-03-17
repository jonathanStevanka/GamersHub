package com.example.gamershub.objectPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.gamershub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomPinnedAdapterClass extends RecyclerView.Adapter<CustomPinnedAdapterClass.customAdap>  {


    private ArrayList<gameHome> pinnedGames;
    private Context context;
    //create a private fragment manager so we can utilize the fragmentransaction inside this class
    private FragmentManager fm;
    public CustomPinnedAdapterClass(@NonNull ArrayList<gameHome> pinnedGames, Context context, FragmentManager fm){
        this.pinnedGames = pinnedGames;
        this.context = context;
        this.fm = fm;
    }


    @NonNull
    @Override
    public customAdap onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pinnedgamesitemlayout,viewGroup,false);
        final CustomPinnedAdapterClass.customAdap customAdap = new CustomPinnedAdapterClass.customAdap(view);


        return customAdap;
    }

    @Override
    public void onBindViewHolder(@NonNull final customAdap viewHolder, int i) {
        final gameHome game = pinnedGames.get(i);
        viewHolder.name.setText(game.getName());
        Picasso.get().load(game.getGameCoverURL()).into(viewHolder.gameCover);

        if (game.getRating()!=null){
            viewHolder.rating.setProgress(game.getRating().intValue());
        }

        if (game.getAggervatedRating()!=null){
            viewHolder.aggervatedRating.setProgress(game.getAggervatedRating().intValue());
        }

        if (game.getTotalRating()!=null){
            viewHolder.totalRating.setProgress(game.getTotalRating().intValue());
        }


    }

    @Override
    public int getItemCount() {
        return pinnedGames.size();
    }

    class customAdap extends RecyclerView.ViewHolder{
        protected TextView name;
        protected ImageView gameCover;

        protected CircleProgressBar rating;
        protected CircleProgressBar aggervatedRating;
        protected CircleProgressBar totalRating;

        public customAdap(View view){
            super(view);
            this.name = view.findViewById(R.id.gameTitlePinned);
            this.gameCover = view.findViewById(R.id.gameImagePinned);

            this.rating = view.findViewById(R.id.ratingPinned);
            this.aggervatedRating = view.findViewById(R.id.ratingAggervatedPinned);
            this.totalRating = view.findViewById(R.id.totalRatingPinned);
        }
    }

}
