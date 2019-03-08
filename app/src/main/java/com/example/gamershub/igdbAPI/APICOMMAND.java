package com.example.gamershub.igdbAPI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;

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
    public ArrayList<gameHome> InitialLoad(final ArrayList<gameHome> arrayList, final CustomHomeAdapterClass customHomeAdapterClass){
        //create an arraylist of jsonobjects so we an split the response we get back later on from JSON

        //int id, String name, String description, String websiteUrl, String imageViewUrl, Double rating, int gameCover, int platform, String releaseDate
        //Add multiple 'gameHome' objects to the 'contents' ArrayList
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
        84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        arrayList.add(new gameHome(2981,"Call of duty","BADBIADWIADWIIUNWDNADWNUIAWDUNAWN","https://www.google.com", "https://www.google.com/image",
                84.86904,2006,48,"2019 March 5th"));
        customHomeAdapterClass.notifyDataSetChanged();

        return arrayList;
    }

    public ArrayList<gameHome> OnRotationChanged(ArrayList<gameHome> arrayList, final CustomHomeAdapterClass customHomeAdapterClass){

        arrayList.add(new gameHome("Test"));
        arrayList.add(new gameHome("Test"));

        customHomeAdapterClass.notifyDataSetChanged();
        return arrayList;
    }
    /**
     * THE METHOD BELOW IS FOR THE INITIAL LAUNCH OF THE DEVICE, IT WILL PULL FROM MANY DIFFERENT CATEGORIES AND FILL RESPECTIVELY
     */
    public void getData(final Context context,final ArrayList<gameHome> arrayList, final CustomHomeAdapterClass customHomeAdapterClass, String search, final String url, final String desiredPlatform){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Game data...please wait");
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


                int platform = 0;

                /**
                 * values below are to be used as finals in the fields below
                 */

                final double MINPop = 85;
                final double MAXRating = 85;

                //create a for loop to programmatically go through the response.
                for (int i = 0; i<response.length(); i++){
                    try {
                        //set the JSONObject to the response
                        jsonObject = response.getJSONObject(i);
                        //create


                        if (url=="games"){
                            final gameHome game = new gameHome();

                            //grab the ID field from the object
                            final int gameId = jsonObject.getInt("id");
                            final String gameName = jsonObject.getString("name");
                            final int gameCover = jsonObject.getInt("cover");
                            final double gameRating = jsonObject.getDouble("rating");
                            final String gameSummary = jsonObject.getString("summary");
                            final String gameWebsiteURL = jsonObject.getString("url");


                            if (jsonObject.has("cover")){
                                AndroidNetworking.post("https://api-v3.igdb.com/covers/").addHeaders("user-key",BuildConfig.IGDBKey)
                                        .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                                        .addStringBody("fields game,height,image_id,url,width; where game = "+ gameId +"; limit 1;")
                                        .setPriority(Priority.IMMEDIATE).build().getAsJSONArray(new JSONArrayRequestListener() {


                                    @Override
                                    public void onResponse(JSONArray response) {
                                        JSONObject localJSON;
                                        try{
                                            for (int r = 0; r < response.length();r++){
                                                localJSON = response.getJSONObject(r);

                                                if (localJSON.has("id")){


                                                    final double height = localJSON.getDouble("height");
                                                    final double width = localJSON.getDouble("width");
                                                    final String tempUrl=localJSON.getString("url");
                                                    final String newUrl = tempUrl.replace("thumb","720p");

                                                    System.out.println(newUrl);


                                                    AndroidNetworking.post("https://api-v3.igdb.com/release_dates/").addHeaders("user-key",BuildConfig.IGDBKey)
                                                            .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                                                            .addStringBody("fields id,game,human,m,platform; where game ="+gameId+"; limit 1;")
                                                            .setPriority(Priority.IMMEDIATE).build().getAsJSONArray(new JSONArrayRequestListener() {

                                                        @Override
                                                        public void onResponse(JSONArray response) {
                                                            JSONObject json = new JSONObject();
                                                            for (int i =0; i < response.length();i++){
                                                                try{
                                                                    json = response.getJSONObject(i);

                                                                    game.setId(gameId);
                                                                    game.setName(gameName);
                                                                    game.setGameCover(gameCover);
                                                                    game.setDescription(gameSummary);
                                                                    game.setRating(gameRating);
                                                                    game.setWebsiteUrl(gameWebsiteURL);
                                                                    game.setHeight(height);
                                                                    game.setWidth(width);
                                                                    game.setGameCoverURL("https:"+newUrl);
                                                                    game.setPlatform(json.getInt("platform"));
                                                                    game.setReleaseDate(json.getString("human"));



                                                                    if (desiredPlatform==null){
                                                                        arrayList.add(game);
                                                                    }
                                                                    if (desiredPlatform=="PS4"){
                                                                        if (game.getPlatform()==48){
                                                                            arrayList.add(game);
                                                                        }
                                                                    }
                                                                    if (desiredPlatform=="XBOX"){
                                                                        if (game.getPlatform()==49){
                                                                            arrayList.add(game);
                                                                        }
                                                                    }
                                                                    if (desiredPlatform=="PC"){
                                                                        if (game.getPlatform()==6){
                                                                            arrayList.add(game);
                                                                        }
                                                                    }
                                                                }catch (JSONException e){
                                                                    e.printStackTrace();
                                                                    e.getCause();
                                                                }
                                                            }
                                                            customHomeAdapterClass.notifyDataSetChanged();
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
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                            e.getCause();
                                        }
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


                            if (jsonObject.has("id")){


                            }
                        }


                        if (url=="release_dates"){

                            //y; where platform = 48; sort popularity desc;
                            final gameHome release_game = new gameHome();


                            final int gameId = jsonObject.getInt("game");
                            //grab the Game name field from the object
                            final int gameObjectID = gameId;

                            if (jsonObject.has("game")) {

                                AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
                                        .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                                        .addStringBody("fields id,name,popularity,cover,rating,summary,url; where id ="+gameId+"; limit 1;")
                                        .setPriority(Priority.IMMEDIATE).build().getAsJSONArray(new JSONArrayRequestListener() {

                                    @Override
                                    public void onResponse(JSONArray response) {
                                        JSONObject json = new JSONObject();
                                        int gameMatchID = 0;
                                        double gameRating = 0;
                                        double gamePop = 0;

                                        try {
                                            //set the JSONObject to the response
                                            for (int r=0; r<response.length();r++){

                                                json = response.getJSONObject(r);

                                                if (json.has("id")){
                                                    gameMatchID = json.getInt("id");

                                                    if (gameMatchID == gameObjectID){

                                                        if (json.has("rating")){
                                                            gameRating = json.getDouble("rating");
                                                        }

                                                        if (json.has("popularity")){
                                                            gamePop = json.getDouble("popularity");
                                                        }

                                                        release_game.setId(gameMatchID);
                                                        release_game.setName(json.getString("name"));

                                                        if (json.has("summary")){
                                                            release_game.setDescription(json.getString("summary"));
                                                        }
                                                        if (json.has("url")){
                                                            release_game.setWebsiteUrl(json.getString("url"));
                                                        }
                                                        if (json.has("rating")){
                                                            release_game.setRating(json.getDouble("rating"));
                                                        }

                                                        if (json.has("cover")){
                                                            release_game.setGameCover(json.getInt("cover"));
                                                        }

                                                    }
                                                }

                                            }
                                            customHomeAdapterClass.notifyDataSetChanged();
                                            progressDialog.dismiss();
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                            e.getCause();
                                        }
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
                    } catch (JSONException e) {
                        //print the stack trace of the error
                        e.printStackTrace();
                        //if theres an error, no more need for the progressDialog
                    }
                }
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
