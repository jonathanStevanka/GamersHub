package com.example.gamershub.objectPackage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.gamershub.R;
import com.example.gamershub.home_screenitemCLICK;
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
    public void onBindViewHolder(@NonNull final customAdap viewHolder,final int i) {
        final gameHome game = pinnedGames.get(i);
        viewHolder.name.setText(game.getName());
        Picasso.get().load(game.getGameCoverURL()).resize(290,390).into(viewHolder.gameCover);

        if (game.getRating()!=null){
            viewHolder.rating.setProgress(game.getRating().intValue());
        }

        if (game.getAggervatedRating()!=null){
            viewHolder.aggervatedRating.setProgress(game.getAggervatedRating().intValue());
        }

        if (game.getTotalRating()!=null){
            viewHolder.totalRating.setProgress(game.getTotalRating().intValue());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a fragmentTransaction and pass the data to another fragment
                Bundle objectBundle = new Bundle();
                //create a new game to be placed inside the bundle
                gameHome gameForBundle = pinnedGames.get(i);
                //check to see if our game bundle is null... which it should never be because we take
                //extra precautions in our other methods to make sure we get no nulls
                if (gameForBundle != null){
                    //gently place the 'gameobject' inside the bundle taking advantage of the easy to use
                    //'putserializable' method.... then all we have to do is make sure our 'gameHome' object
                    //implements serializable and that got rid of our other error here.
                    objectBundle.putSerializable("game", gameForBundle);
                }
                //create a new fragment transaction utilizing the FM we passed into the constructor earlier on
                FragmentTransaction transaction = fm.beginTransaction();
                //because we cant directly instantiate the 'home_screenitemCLICK()'
                //directly inside the transaction.replace method, we created a new object of that class

                //create a new object of the class we want this data to be sent to
                home_screenitemCLICK test = new home_screenitemCLICK();
                //add the bundle to the new class we just created
                test.setArguments(objectBundle);
                //replace the view on 'mainactivity' with the new class object we just created, this will call its oncreate
                transaction.replace(R.id.content, test);
                //add to backstack so the user can come back to the homepage
                transaction.addToBackStack(null);
                //commit the transaction
                transaction.commit();
            }
        });


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

    public void RemoveAllData(){
        pinnedGames.clear();
        notifyDataSetChanged();
    }

}
