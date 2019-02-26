package com.example.gamershub.igdbAPI;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.gamershub.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class APICOMMAND {


    /**
     * @author Jonathan stevanka
     * @description This class 'APICOMMAND' is a custom class that utilizes more custom search methods
     * depending on what the user would be looking for
     */


    /**
     * THE METHODS BELOW ARE FOR SEARCHING "GAMES" on the API
     */
    public ArrayList<JSONObject> SearchGames(String search){
        //create an arraylist of jsonobjects so we an split the response we get back later on from JSON
        final ArrayList<JSONObject> contents = new ArrayList<JSONObject>();

        //check to see if the search is empty
        //MAKE THIS A TOAST MESSAGE BEFORE RELEASE
        if(search.isEmpty()==true){
            System.out.println("The search field is empty!");
            return contents;
        }
        //set up the POST request for this current search
        AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
                .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                .addStringBody("search \""+search+"\"; fields name,popularity,rating; where rating > 75; limit 15;")
                .setPriority(Priority.HIGH).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                //use the string search method to retreive whatever the user was searching for.
                //this allows for easy modification, and we seperate it into arraylists for easy implementation into card views
                for(int i=0; i < response.length(); i++){
                    //add the contents to our local arraylist
                    contents.add(response.optJSONObject(i));
                    //TESTING-print to the console1
                    //System.out.println(contents.get(i));
                }
                dumpGameInfo(contents);
            }

            @Override
            public void onError(ANError anError) {
                System.out.println(anError.getErrorBody());
            }
        });

        return contents;
    }

    /**
     * THE METHODS BELOW ARE FOR SEARCHING "COMPANIES" on the API
     */

    public ArrayList<JSONObject> SearchCompanies(String search){
        //create an arraylist of jsonobjects so we an split the response we get back later on from JSON
        final ArrayList<JSONObject> contents = new ArrayList<JSONObject>();

        //check to see if the search is empty
        //MAKE THIS A TOAST MESSAGE BEFORE RELEASE
        if(search.isEmpty()==true){
            System.out.println("The search field is empty!");
            return contents;
        }
        //set up the POST request for this current search
        AndroidNetworking.post("https://api-v3.igdb.com/companies/").addHeaders("user-key",BuildConfig.IGDBKey)
                .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                .addStringBody("fields name,url,description,country; where name =\""+search+"\";")
                .setPriority(Priority.HIGH).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                //use the string search method to retreive whatever the user was searching for.
                //this allows for easy modification, and we seperate it into arraylists for easy implementation into card views
                for(int i=0; i < response.length(); i++){
                    //add the contents to our local arraylist
                    contents.add(response.optJSONObject(i));
                    //TESTING-print to the console1
                    //System.out.println(contents.get(i));
                }
                dumpCompanyInfo(contents);
            }

            @Override
            public void onError(ANError anError) {
                System.out.println(anError.getErrorBody());
            }
        });

        return contents;
    }

    /**
     * THE METHODS BELOW ARE FOR SEARCHING "CHARACTERS" on the API
     */



    /**
     * THE METHODS BELOW ARE FOR SEARCHING "GAME_ENGINES" on the API
     */



    /**
     * THE METHODS BELOW ARE FOR SEARCHING "SPECIFIC GENERE" on the API
     */


    public void dumpGameInfo(ArrayList<JSONObject> jsonObjects){

        for(int i=0; i < jsonObjects.size();i++){
            //System.out.println(jsonObjects.get(i));
            try{
                String gameName = jsonObjects.get(i).getString("name");
                Double gamePop = jsonObjects.get(i).getDouble("popularity");
                Double gameRating = jsonObjects.get(i).getDouble("rating");

                System.out.println("Name: "+gameName+"\n" +
                        "Popularity: "+gamePop+"\n" +
                        "Rating: "+gameRating+"\n" +
                        "-----------------------------");
            }catch(JSONException anError){

            }
        }
    }

    public void dumpCompanyInfo(ArrayList<JSONObject> jsonObjects){

        for(int i=0; i < jsonObjects.size();i++){
            //System.out.println(jsonObjects.get(i));
            try{
                String companyName = jsonObjects.get(i).getString("name");
                String companyUrl = jsonObjects.get(i).getString("url");
                String companyDesc = jsonObjects.get(i).getString("description");
                String companyCountry = jsonObjects.get(i).getString("country");

                System.out.println("Name: "+companyName+"\n" +
                        "Website: "+companyUrl+"\n" +
                        "Description: "+companyDesc+"\n" +
                        "Country: "+companyCountry+"\n" +
                        "-------------------------------------");
            }catch(JSONException anError){
                System.out.println(anError.getCause());
            }
        }
    }

}
