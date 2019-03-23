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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamershub.Database.DatabaseHelper;
import com.example.gamershub.R;
import com.example.gamershub.home_screenitemCLICK;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomHomeAdapterClass extends RecyclerView.Adapter<CustomHomeAdapterClass.customAdap> {


    private ArrayList<gameHome> games;
    private Context context;
    //create a private fragment manager so we can utilize the fragmentransaction inside this class
    private FragmentManager fm;
    public CustomHomeAdapterClass(@NonNull ArrayList<gameHome> games, Context context, FragmentManager fm){
        this.games = games;
        this.context = context;
        this.fm = fm;
    }


    @NonNull
    @Override
    public customAdap onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homeitemlayout,viewGroup,false);
        final customAdap customAdap = new customAdap(view);



        return customAdap;
    }

    @Override
    public void onBindViewHolder(@NonNull final customAdap viewHolder, final int i) {
        final gameHome game = games.get(i);
        viewHolder.name.setText(game.getName());
        Picasso.get().load(game.getGameCoverURL()).resize(290,390).into(viewHolder.gameCover);
        viewHolder.setIsRecyclable(true);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a fragmentTransaction and pass the data to another fragment
                Bundle objectBundle = new Bundle();
                //create a new game to be placed inside the bundle
                gameHome gameForBundle = games.get(i);
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


                /**
                 * add the top params to an object.
                 * this will then be passed to our 'homeLargeItemClickCardView'
                 */

            }
        });

    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class customAdap extends RecyclerView.ViewHolder{
        protected TextView name;
        protected ImageView gameCover;

        public customAdap(View view){
            super(view);
            this.name = view.findViewById(R.id.gameTitle);
            this.gameCover = view.findViewById(R.id.gameImage);
        }
    }

}
