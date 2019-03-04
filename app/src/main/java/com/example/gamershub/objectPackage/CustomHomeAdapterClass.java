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
    public void onBindViewHolder(@NonNull customAdap viewHolder, final int i) {
        final gameHome game = games.get(i);
        viewHolder.name.setText(game.getName());
        //Picasso.get().load(game.getImageViewUrl()).into(viewHolder.url);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = games.get(i).getId();
                String name = games.get(i).getName();
                String description = games.get(i).getDescription();
                String websiteUrl = games.get(i).getWebsiteUrl();
                String imageViewUrl = games.get(i).getImageViewUrl();
                Double rating = games.get(i).getRating();
                int gameCover = games.get(i).getGameCover();
                int platform = games.get(i).getPlatform();
                String releaseDate = games.get(i).getReleaseDate();


                System.out.println("-----------------------------");
                System.out.println("id: "+id);
                System.out.println("name: "+name);
                System.out.println("description: "+description);
                System.out.println("websiteURL: "+websiteUrl);
                System.out.println("imageURL: "+imageViewUrl);
                System.out.println("rating: "+rating);
                System.out.println("gameCover: "+gameCover);
                System.out.println("platform: "+platform);
                System.out.println("releaseDate: "+releaseDate);
                System.out.println("-----------------------------");
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
        protected ImageView url;

        public customAdap(View view){
            super(view);
            this.name = view.findViewById(R.id.gameTitle);
            this.url = view.findViewById(R.id.gameImage);
        }
    }

}
