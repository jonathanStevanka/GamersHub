package com.example.gamershub.objectPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamershub.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CustomCommentAdapterClass extends RecyclerView.Adapter<CustomCommentAdapterClass.customAdap> {

    //create an arraylist to hold our comments
    private ArrayList<commentObject> comments;
    //grab the context from the current fragment
    private Context context;

    public CustomCommentAdapterClass(@NonNull ArrayList<commentObject> comments,Context context){
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public customAdap onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.commentlayout,viewGroup,false);
        //create a new customadapter and attach the view to the adapter
        final customAdap customAdap = new customAdap(view);


        return customAdap;
    }

    @Override
    public void onBindViewHolder(@NonNull customAdap viewHolder, int i) {
        commentObject comment = comments.get(i);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d yyyy, HH:mm:ss");
        Date createdDate = null;
        createdDate = new Date(TimeUnit.MILLISECONDS.convert(Long.valueOf(comment.getCreatedAt()),TimeUnit.SECONDS));
        //System.out.println("UNIX: "+createdDate.toLocaleString());
        viewHolder.createdAT.setText(createdDate.toLocaleString());
        viewHolder.userName.setText(String.valueOf("Posted by: "+comment.getUserID()));
        viewHolder.reviewContent.setText(comment.getReviewContent());
        viewHolder.feedLikes.setText(String.valueOf(comment.getReviewLikes()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class customAdap extends RecyclerView.ViewHolder{
        protected TextView userName;
        protected TextView createdAT;
        protected TextView reviewContent;
        protected TextView feedLikes;
//        protected ImageView UserImage;

        public customAdap(View view){
            super(view);
            this.userName = view.findViewById(R.id.userName);
//            this.UserImage = view.findViewById(R.id.userCover);
            this.createdAT = view.findViewById(R.id.postedAt);
            this.reviewContent = view.findViewById(R.id.reviewContent);
            this.feedLikes = view.findViewById(R.id.thumbsUpCounter);
        }
    }
}
