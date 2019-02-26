package com.example.gamershub.igdbAPI;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.gamershub.BuildConfig;

import org.json.JSONArray;

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

    public void Search(AndroidNetworking androidNetworking, String search){
        AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key", BuildConfig.IGDBKey)
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


}
