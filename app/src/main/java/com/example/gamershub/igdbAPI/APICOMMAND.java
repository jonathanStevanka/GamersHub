package com.example.gamershub.igdbAPI;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.gamershub.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class APICOMMAND {


    public void Search(AndroidNetworking androidNetworking){
        AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
                .addHeaders("Accept","application/json").addStringBody("fields name,popularity,rating; sort popularity desc;")
                .setPriority(Priority.MEDIUM).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    public ArrayList<JSONObject> Search(String search){
        //create an arraylist of jsonobjects so we an splice the response we get back later on from JSON
        final ArrayList<JSONObject> contents = new ArrayList<JSONObject>();

        AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
                .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded").addStringBody("search \""+search+"\"; fields name,popularity,rating;")
                .setPriority(Priority.HIGH).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                //use the string search method to retreive whatever the user was searching for.
                //this allows for easy modification, and we seperate it into arraylists for easy implementation into card views
                for(int i=0; i < response.length(); i++){
                    //add the contents to our local arraylist
                    contents.add(response.optJSONObject(i));
                    //TESTING-print to the console1
                    System.out.println(contents.get(i));
                }

            }

            @Override
            public void onError(ANError anError) {
                System.out.println(anError.getErrorBody());
            }
        });

        return contents;
    }

    public void dumpInfo(){

    }

}
