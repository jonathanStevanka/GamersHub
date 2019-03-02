package com.example.gamershub.igdbAPI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.gamershub.BuildConfig;
import com.example.gamershub.R;
import com.example.gamershub.objectPackage.CustomHomeAdapterClass;
import com.example.gamershub.objectPackage.gameHome;

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
        //set up the POST request for this current search
        AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
                .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                .addStringBody("search \""+search+"\"; fields id,name,popularity,rating,screenshots.*; where rating > 75; limit 10;")
                .setPriority(Priority.IMMEDIATE).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i<response.length(); i++){
                    contents.add(response.optJSONObject(i));
                }
                dumpGameInfo(contents);
            }
            @Override
            public void onError(ANError anError) {
                System.out.println(anError.getErrorCode());
                System.out.println(anError.getErrorBody());
                System.out.println(anError.getErrorDetail());
                System.out.println(anError.getResponse());
            }
        });

    return contents;
    }

    //test function
    public String SearchGameID(int ID){


        final gameHome game = new gameHome();

        AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
                .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                .addStringBody("fields name,summary; where id ="+ID+"; limit 1;")
                .setPriority(Priority.IMMEDIATE).build().getAsJSONArray(new JSONArrayRequestListener() {

            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0; i<response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        int gameId = jsonObject.getInt("id");
                        String gameName = jsonObject.getString("name");
                        System.out.println("-----------------------------");

                        System.out.println("SearchGameWithID: "+gameId);
                        System.out.println("SearchGameWithID: "+gameName);

                        System.out.println("-----------------------------");

                        game.setId(gameId);

                        if (gameName != null){
                            game.setName(gameName);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(ANError anError) {

            }

        });

        return game.getName();
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


    public ArrayList<gameHome> dumpGameInfo(ArrayList<JSONObject> jsonObjects){


        final ArrayList<gameHome> contents = new ArrayList<gameHome>();

        System.out.println(jsonObjects.size());
        System.out.println(jsonObjects);
        for(int i=0; i < jsonObjects.size();i++){
            gameHome game = null;
            try{
                int gameId = jsonObjects.get(i).getInt("id");
                String gameName = jsonObjects.get(i).getString("name");
                Double gamePop = jsonObjects.get(i).getDouble("popularity");
                Double gameRating = jsonObjects.get(i).getDouble("rating");
                String gameScreenCapUrl = jsonObjects.get(i).getString("screenshots");
//                System.out.println("Name: "+gameName+"\n" +
//                        "ID: " + gameId+"\n" +
//                        "Popularity: "+gamePop+"\n" +
//                        "Rating: "+gameRating+"\n" +
//                        "Screenshot location: "+gameScreenCapUrl+"\n" +
//                        "-----------------------------");


                //add this information too the objectarray to be passed out
                game = new gameHome(gameId,gameName,gameRating,"https://images.igdb.com/igdb/image/upload/t_thumb/d4iizhkgl3vmc2btyhpw.jpg");
                System.out.println("--------------------------");

                System.out.println(game.getId());
                System.out.println(game.getName());
                System.out.println(game.getRating());
                System.out.println(game.getImageViewUrl());

                System.out.println("--------------------------");

                contents.add(game);
            }catch(JSONException anError){
                System.out.println(anError.getCause());
                System.out.println(anError.getStackTrace());

            }
        }

        return contents;
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


    /**
     * THIS IS THE DEFAULT CONSTRUCTER TO START POPULATING GAMES IN THE HOMESCREEN ***OFFLINE***
     */
    public ArrayList<gameHome> InitialLoad(){
        //create an arraylist of jsonobjects so we an split the response we get back later on from JSON
        final ArrayList<gameHome> contents = new ArrayList<gameHome>();

        //Add multiple 'gameHome' objects to the 'contents' ArrayList
        contents.add(new gameHome("Red dead redemption"));
        contents.add(new gameHome("Call of duty"));
        contents.add(new gameHome("Zelda"));
        contents.add(new gameHome("Resident Evil"));
        contents.add(new gameHome("Kill bill"));


        return contents;
    }


    /**
     * THE METHOD BELOW IS FOR THE INITIAL LAUNCH OF THE DEVICE, IT WILL PULL FROM MANY DIFFERENT CATEGORIES AND FILL RESPECTIVELY
     */



    public ArrayList<gameHome> getData(final Context context, final ArrayList<gameHome> arrayList, final CustomHomeAdapterClass customHomeAdapterClass, String search, final String url){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Game data...");
        progressDialog.show();



        AndroidNetworking.post("https://api-v3.igdb.com/"+url+"/").addHeaders("user-key",BuildConfig.IGDBKey)
                .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                .addStringBody(search)
                .setPriority(Priority.IMMEDIATE).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {

                //Instantiate a new JSONObject for the response information
                JSONObject jsonObject = null;
                //Instantiate a new 'gameHome' Object so we can add data to the arraylist
                gameHome game = null;


                int gameId = 0;
                String gameName = null;
                int gameCover = 0;
                Double gameRating = 0.0;
                String gameSummary = null;
                String gameWebsiteURL = null;
                String gameReleaseDate = null;
                int platform = 0;

                //create a for loop to programmatically go through the response.
                for (int i = 0; i<response.length(); i++){
                    try {
                        //set the JSONObject to the response
                        jsonObject = response.getJSONObject(i);
                        //create
                        game = new gameHome();


                        if (url=="games"){
                            //grab the ID field from the object
                            gameId = jsonObject.getInt("id");
                            //grab the Game name field from the object
                            gameName = jsonObject.getString("name");
                            gameCover = jsonObject.getInt("cover");
                            if (jsonObject.has("rating")){
                                gameRating = jsonObject.getDouble("rating");
                            }
                            gameSummary = jsonObject.getString("summary");
                            gameWebsiteURL = jsonObject.getString("url");

//                                System.out.println("-----------------------");
//                                System.out.println(gameId);
//                                System.out.println(gameName);
//                                System.out.println(gameCover);
//                                System.out.println(gameSummary);
//                                System.out.println(gameWebsiteURL);
//                                System.out.println("-----------------------");

                            game.setId(gameId);
                            game.setName(gameName);
                            game.setGameCover(gameCover);
                            game.setDescription(gameSummary);
                            game.setRating(gameRating);
                            game.setWebsiteUrl(gameWebsiteURL);

                            arrayList.add(game);
                        }


                        if (url=="release_dates"){

                            //y; where platform = 48; sort popularity desc;
                            final gameHome release_game = new gameHome();


                            gameId = jsonObject.getInt("game");
                            //grab the Game name field from the object
                            final int gameObjectID = gameId;

                            if (jsonObject.has("game")) {

                                AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
                                        .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                                        .addStringBody("fields name,popularity,cover,rating,summary,url; where id ="+gameId+"; limit 1;")
                                        .setPriority(Priority.IMMEDIATE).build().getAsJSONArray(new JSONArrayRequestListener() {

                                    @Override
                                    public void onResponse(JSONArray response) {
                                        JSONObject json = new JSONObject();
                                        int gameMatchID = 0;
                                        try {
                                            //set the JSONObject to the response
                                            for (int r=0; r<response.length();r++){

                                                json = response.getJSONObject(r);

                                                if (json.has("id")){
                                                    gameMatchID = json.getInt("id");
                                                    if (gameMatchID == gameObjectID){
                                                        release_game.setId(gameMatchID);
                                                        release_game.setName(json.getString("name"));
                                                        release_game.setDescription(json.getString("summary"));
                                                        arrayList.add(release_game);
                                                    }
                                                }

                                            }

                                        }catch (JSONException e){

                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {

                                    }

                                });

                            }
                            //gameReleaseDate = jsonObject.getString("human");
                            //platform = jsonObject.getInt("platform");
                        }

                        //System.out.println("TrendingPs4: "+arrayList.size());

                    } catch (JSONException e) {
                        //print the stack trace of the error
                        e.printStackTrace();
                        //if theres an error, no more need for the progressDialog
                        progressDialog.dismiss();
                    }
                }
                //use the 'notifyDataSetChanged' method so it will re-build the contents
                customHomeAdapterClass.notifyDataSetChanged();
                //dismiss the dialog
                progressDialog.dismiss();
                //dumpGameInfo(contents);
            }
            @Override
            public void onError(ANError anError) {
                System.out.println(anError.getErrorCode());
                System.out.println(anError.getErrorBody());
                System.out.println(anError.getErrorDetail());
                System.out.println(anError.getResponse());            }
        });



        return arrayList;
    }

    public void getDataFromGameURL(final Context context, final ArrayList<gameHome> arrayList, final CustomHomeAdapterClass customHomeAdapterClass , final String url){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Mowing the Lawn...");
        progressDialog.show();

        gameHome test = null;


            AndroidNetworking.post("https://api-v3.igdb.com/"+url+"/").addHeaders("user-key",BuildConfig.IGDBKey)
                    .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                    .addStringBody("fields name,popularity,cover,rating,summary,url; where id ="+test.getId()+"; limit 1;")
                    .setPriority(Priority.IMMEDIATE).build().getAsJSONArray(new JSONArrayRequestListener() {
                @Override
                public void onResponse(JSONArray response) {

                    //create a new 'localArrayList'
                    //this will be used to grab information from the Game url to then be added to our upcoming games arraylist
                    ArrayList<gameHome> arrayListLocal = new ArrayList<gameHome>();

                    //Instantiate a new JSONObject for the response information
                    JSONObject jsonObject = null;
                    //Instantiate a new 'gameHome' Object so we can add data to the arraylist
                    gameHome game = null;

                    int gameId = 0;
                    String gameName = null;
                    Double gameCover = null;
                    Double gameRating = null;
                    String gameSummary = null;
                    String gameWebsiteURL = null;
                    String gameReleaseDate = null;
                    Double platform = null;

                    for (int i=0; i<response.length();i++){
                        try{
                            //grab the ID field from the object
                            gameId = jsonObject.getInt("id");
                            //grab the Game name field from the object
                            gameName = jsonObject.getString("name");
                            gameCover = jsonObject.getDouble("cover");
                            gameRating = jsonObject.getDouble("rating");
                            gameSummary = jsonObject.getString("summary");
                            gameWebsiteURL = jsonObject.getString("url");

                                System.out.println("-----------------------");
                                System.out.println(gameId);
                                System.out.println(gameName);
                                System.out.println(gameCover);
                                System.out.println(gameSummary);
                                System.out.println(gameWebsiteURL);
                                System.out.println("-----------------------");
                        }catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }

                    //create a for loop to programmatically go through the response.
                    //programatically add the data to the already preexisting arraylist of information.
                    customHomeAdapterClass.notifyDataSetChanged();
                    //dismiss the dialog
                    progressDialog.dismiss();
                }
                @Override
                public void onError(ANError anError) {
                    System.out.println(anError.getErrorCode());
                    System.out.println(anError.getErrorBody());
                    System.out.println(anError.getErrorDetail());
                    System.out.println(anError.getResponse());
                }

            });

        }
    }
