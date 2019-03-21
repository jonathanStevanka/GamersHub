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
import com.example.gamershub.Database.DatabaseHelper;
import com.example.gamershub.HomeScreen;
import com.example.gamershub.R;
import com.example.gamershub.objectPackage.CustomHomeAdapterClass;
import com.example.gamershub.objectPackage.CustomPinnedAdapterClass;
import com.example.gamershub.objectPackage.gameHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static java.text.DateFormat.getDateInstance;
import static java.text.DateFormat.getDateTimeInstance;

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


    public ArrayList<gameHome> dumpGameInfo(ArrayList<JSONObject> jsonObjects) {


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
     * THE BELOW METHODS WILL FIND DATA LOCALLY STORED ON THE PHONE AND UPDATE THE RECYCLERVIEWS WHEN NECCESARY
     */

    public void loadDataFromLocal(final Context context,DatabaseHelper db, final  CustomHomeAdapterClass customHomeAdapterClass,final ArrayList<gameHome> arrayList,final ArrayList<gameHome> localArraylist,String destination){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Discovering local data...please wait");
        progressDialog.show();


        for (int i=0;i<localArraylist.size();i++){
            //System.out.println("APICOMMAND@loadDataFromLocal: Total size of Database= "+db.grabAllGames().size());
            //System.out.println("APICOMMAND@loadDataFromLocal: Total size of localArraylist= "+localArraylist.size());
            //System.out.println("APICOMMAND@loadDataFromLocal: "+localArraylist.get(i).getName() + " TIME DATA WAS ADDED TO DEVICE: "+localArraylist.get(i).getTimestamp());

            if (arrayList.contains(localArraylist.get(i))){
                //System.out.println("APICOMMAND@loadDataFromLocal: "+localArraylist.get(i));

            }else {

                if (localArraylist.get(i).getRecyclerviewTopic().contains(destination)){
                    arrayList.add(localArraylist.get(i));
                }
            }
        }
        customHomeAdapterClass.notifyDataSetChanged();
        progressDialog.dismiss();
    }


    public void loadDataFromPopularLocal(final Context context,DatabaseHelper db, final  CustomHomeAdapterClass customHomeAdapterClass,final ArrayList<gameHome> arrayList,final ArrayList<gameHome> localArraylist,String destination,final ArrayList<gameHome> trendingGames,int gamePlatform){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Discovering local data...please wait");
        progressDialog.show();

        String[] platforms;

        int currentPlat;
        int gamePlatformG;

       //System.out.println(destination+"trendingGamesSize: "+ trendingGames.size());
        for (int i=0;i<trendingGames.size();i++){
            platforms = new String[trendingGames.size()];
            platforms = trendingGames.get(i).getPlatformsTest().replace("[","").replace("]","").split(",");

            for (int r=0; r<platforms.length;r++){

                gamePlatformG = Integer.valueOf(platforms[r]);

                if (gamePlatform==gamePlatformG && !trendingGames.get(i).getRecyclerviewTopic().contentEquals("upcomingGames")){
                    arrayList.add(trendingGames.get(i));
                }
            }
            //System.out.println("------------------------------");
        }
        if (!destination.contains("upcomingGames")){
            Collections.shuffle(arrayList);
        }
        customHomeAdapterClass.notifyDataSetChanged();
        progressDialog.dismiss();
        db.close();
    }


    public void loadDataFromLocalPinnedGames(final Context context, DatabaseHelper db, final CustomPinnedAdapterClass customHomeAdapterClass, final ArrayList<gameHome> arrayList, final ArrayList<gameHome> localArraylist){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Discovering local data...please wait");
        progressDialog.show();

        /**
         * This method will load data from localDB into gameHome objects to be then populated into the recyclerviews
         */

        //create an arraylist that conforms to 'gameHome'
        //grab the database data


        for (int i=0;i<localArraylist.size();i++){
            //System.out.println("APICOMMAND@loadDataFromLocal: Total size of Database= "+db.grabAllGames().size());
            //System.out.println("APICOMMAND@loadDataFromLocal: Total size of localArraylist= "+localArraylist.size());
            //System.out.println("APICOMMAND@loadDataFromLocal: "+localArraylist.get(i).getName() + " TIME DATA WAS ADDED TO DEVICE: "+localArraylist.get(i).getTimestamp());

            //System.out.println(localArraylist.get(i).getIspinned());
            if (!localArraylist.isEmpty()){
                if (arrayList.contains(localArraylist.get(i))){
                    //System.out.println("APICOMMAND@loadDataFromLocal: "+localArraylist.get(i));
                    continue;
                }else {
                    if (localArraylist.get(i).getIspinned().contains("yes")){
                        arrayList.add(localArraylist.get(i));
                    }
                }
            }

        }

        customHomeAdapterClass.notifyDataSetChanged();
        progressDialog.dismiss();
        db.close();
    }

    /**
     * THE METHOD BELOW IS FOR THE INITIAL LAUNCH OF THE DEVICE, IT WILL PULL FROM MANY DIFFERENT CATEGORIES AND FILL RESPECTIVELY
     */


    public void getData(final Context context, final ArrayList<gameHome> arrayList, final CustomHomeAdapterClass customHomeAdapterClass, String search, final String url, final String desiredPlatform, final String destination,final ArrayList<gameHome> popularOnPS4,final ArrayList<gameHome> popularOnXBOX,final ArrayList<gameHome> popularOnPC){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Game data...please wait");
        progressDialog.show();

        //create a timestamp at the start so we can utilize this in the loading part of our application
        //it will check if the data is older than 24hrs and if so will then update with new data
        final String currentDateTimeStamp = getDateTimeInstance().format(new Date());

        AndroidNetworking.post("https://api-v3.igdb.com/"+url+"/").addHeaders("user-key",BuildConfig.IGDBKey)
                .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                .addStringBody(search)
                .setPriority(Priority.LOW).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {

                //Instantiate a new JSONObject for the response information
                JSONObject jsonObject = null;
                //Instantiate a new 'gameHome' Object so we can add data to the arraylist

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

                        if (url=="games") {
                                    DatabaseHelper db = new DatabaseHelper(context);
                                    //Instantiate a new JSONObject for the response information
                                    JSONObject jsonGameObject = null;
                                    JSONArray jsonScreenshotArray = null;
                                    JSONArray jsonReleaseObject = null;
                                    JSONObject jsonCoverObject = null;
                                    gameHome game;

                                    try {
                                            game = new gameHome();

                                            jsonGameObject = response.getJSONObject(i);


                                            if (jsonGameObject.has("id")) {
                                                game.setId(jsonGameObject.getInt("id"));
                                            }

                                            if (jsonGameObject.has("name")) {
                                                game.setName(jsonGameObject.getString("name"));
                                            }

                                            if (jsonGameObject.has("platforms")) {
                                                game.setPlatformsTest(jsonGameObject.getString("platforms"));
                                            }
                                            //popularity goes here
                                            if (jsonGameObject.has("rating")) {
                                                //double gameRating = jsonGameObject.getDouble("rating");
                                                game.setRating(jsonGameObject.getDouble("rating"));
                                            }

                                            if (jsonGameObject.has("release_dates")) {
                                                jsonReleaseObject = jsonGameObject.getJSONArray("release_dates");

                                            }

                                            if (jsonGameObject.has("cover")) {
                                                jsonCoverObject = jsonGameObject.getJSONObject("cover");
                                                game.setGameCover(jsonCoverObject.getInt("id"));
                                                game.setGameCoverURL("https:" + jsonCoverObject.getString("url").replace("thumb", "720p"));
                                            }
                                            if (jsonGameObject.has("screenshots")) {
                                                jsonScreenshotArray = jsonGameObject.getJSONArray("screenshots");
                                                String[] screenshotUrlsUnextended = new String[jsonScreenshotArray.length()];
                                                for (int r = 0; r < jsonScreenshotArray.length(); r++) {
                                                    screenshotUrlsUnextended[r] = "https:" + jsonScreenshotArray.getJSONObject(r).getString("url").replace("thumb", "720p");
                                                    System.out.println("'" + screenshotUrlsUnextended[r] + "'");
                                                }
                                                game.setGameScreenshots(screenshotUrlsUnextended);
                                            }
                                            if (jsonGameObject.has("aggregated_rating")) {
                                                game.setAggervatedRating(jsonGameObject.getDouble("aggregated_rating"));
                                            }
                                            if (jsonGameObject.has("summary")) {
                                                game.setDescription(jsonGameObject.getString("summary"));
                                            }
                                            if (jsonGameObject.has("total_rating")) {
                                                game.setTotalRating(jsonGameObject.getDouble("total_rating"));
                                            }
                                            if (jsonGameObject.has("url")) {
                                                game.setWebsiteUrl(jsonGameObject.getString("url"));

                                            }
                                            game.setIspinned("no");
                                            game.setRecyclerviewTopic(destination);

                                            System.out.println("----------------------------------------------------");
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME ID - " + game.getId());
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME NAME - " + game.getName());
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME COVERURL - " + game.getGameCoverURL());
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME PLATFORMS - " + game.getPlatformsTest());
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME AGGERVATED_RATING - " + game.getAggervatedRating());
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME RATING - " + game.getRating());
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME TOTALRATING - " + game.getRating());
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME SCREENSHOTS - " + game.getGameScreenshotExtendedURL());
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME SUMMARY - " + game.getDescription());
                                            System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME WEBURL - " + game.getWebsiteUrl());
                                            System.out.println("----------------------------------------------------");


                                        String[] platformFromJSON = game.getPlatformsTest().replace("[","")
                                                .replace("]","").split(",");
                                        int gamePlatform;
                                        System.out.println("--------------------------------------------------");
                                        System.out.println("APICOMMAND@GETDATA:551- game name: "+game.getName());

                                        for (int r=0; r<platformFromJSON.length;r++) {
                                            gamePlatform = Integer.valueOf(platformFromJSON[r]);
                                            System.out.println("APICOMMAND@GETDATA:551- game platform: " + gamePlatform);

                                            if (gamePlatform == 6 && !game.getRecyclerviewTopic().contentEquals("upcomingGames")) {
                                                System.out.println("APICOMMAND@GETDATA:551- Game has been added");
                                                popularOnPC.add(game);
                                            }

                                            if (gamePlatform == 48 && !game.getRecyclerviewTopic().contentEquals("upcomingGames")) {
                                                System.out.println("APICOMMAND@GETDATA:551- Game has been added");
                                                popularOnPS4.add(game);
                                            }

                                            if (gamePlatform == 49 && !game.getRecyclerviewTopic().contentEquals("upcomingGames")){
                                                System.out.println("APICOMMAND@GETDATA:551- Game has been added");
                                                popularOnXBOX.add(game);
                                            }

                                        }

                                        if (!popularOnPC.isEmpty() && popularOnPS4.isEmpty() && popularOnXBOX.isEmpty()){
                                            Collections.shuffle(popularOnPC);
                                            Collections.shuffle(popularOnPS4);
                                            Collections.shuffle(popularOnXBOX);
                                        }

                                        arrayList.add(game);
                                        db.addGame(game);
                                        customHomeAdapterClass.notifyDataSetChanged();
                                        progressDialog.dismiss();
                                        db.close();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        e.getCause();
                                    }

                        }


                        if (url=="release_dates"){

                            //y; where platform = 48; sort popularity desc;
                            final gameHome release_game = new gameHome();


                            //grab the ID field from the object
                            final int gameId = jsonObject.getInt("game");


                            //grab the Game name field from the object
                            final int gameObjectID = gameId;

                            if (jsonObject.has("game")) {

                                AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
                                        .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                                        .addStringBody("fields id,name,popularity,cover,rating,aggregated_rating,total_rating,summary,platforms,url,screenshots; where id ="+gameId+"; limit 1;")
                                        .setPriority(Priority.LOW).build().getAsJSONArray(new JSONArrayRequestListener() {

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

                                                        release_game.setId(gameMatchID);
                                                        release_game.setName(json.getString("name"));
                                                        release_game.setRecyclerviewTopic(destination);
                                                        release_game.setIspinned("no");

                                                        if (json.has("platforms")){
                                                            release_game.setPlatformsTest(json.getString("platforms"));
                                                        }
                                                        if (json.has("popularity")){
                                                            release_game.setPopularity(json.getDouble("popularity"));
                                                        }
                                                        if (json.has("summary")){
                                                            release_game.setDescription(json.getString("summary"));
                                                        }
                                                        if (json.has("url")){
                                                            release_game.setWebsiteUrl(json.getString("url"));
                                                        }
                                                        if (json.has("rating")){
                                                            release_game.setRating(json.getDouble("rating"));
                                                        }
                                                        if (json.has("aggregated_rating")){
                                                            release_game.setAggervatedRating(json.getDouble("aggregated_rating"));
                                                        }
                                                        if (json.has("total_rating")){
                                                            release_game.setTotalRating(json.getDouble("total_rating"));
                                                        }
                                                        if (json.has("cover")){
                                                            AndroidNetworking.post("https://api-v3.igdb.com/covers/").addHeaders("user-key",BuildConfig.IGDBKey)
                                                                    .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                                                                    .addStringBody("fields game,height,image_id,url,width; where game = "+ gameId +"; limit 1;")
                                                                    .setPriority(Priority.LOW).build().getAsJSONArray(new JSONArrayRequestListener() {


                                                                @Override
                                                                public void onResponse(JSONArray response) {
                                                                    JSONObject localJSON;
                                                                    try{
                                                                        for (int r = 0; r < response.length();r++){
                                                                            localJSON = response.getJSONObject(r);

                                                                            if (localJSON.has("url")){
                                                                                final String tempUrl=localJSON.getString("url");
                                                                                final String newUrl = tempUrl.replace("thumb","720p");
                                                                                release_game.setGameCoverURL("https:"+newUrl);
                                                                            }

                                                                            if (localJSON.has("id")){


                                                                                final double height = localJSON.getDouble("height");
                                                                                final double width = localJSON.getDouble("width");
                                                                                final String tempUrl=localJSON.getString("url");
                                                                                final String newUrl = tempUrl.replace("thumb","720p");

                                                                                //System.out.println(newUrl);


                                                                                AndroidNetworking.post("https://api-v3.igdb.com/release_dates/").addHeaders("user-key",BuildConfig.IGDBKey)
                                                                                        .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                                                                                        .addStringBody("fields id,game,human,m; where game ="+release_game.getId()+"; limit 1;")
                                                                                        .setPriority(Priority.LOW).build().getAsJSONArray(new JSONArrayRequestListener() {

                                                                                    @Override
                                                                                    public void onResponse(JSONArray response) {
                                                                                        JSONObject json = new JSONObject();
                                                                                        for (int i =0; i < response.length();i++){
                                                                                            try{
                                                                                                json = response.getJSONObject(i);

                                                                                                release_game.setHeight(height);
                                                                                                release_game.setWidth(width);
                                                                                                release_game.setReleaseDate(json.getString("human"));
                                                                                                release_game.setTimestamp(currentDateTimeStamp);


                                                                                            }catch (JSONException e){
                                                                                                e.printStackTrace();
                                                                                                e.getCause();
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
                                                        if (json.has("screenshots")){


                                                            AndroidNetworking.post("https://api-v3.igdb.com/screenshots/").addHeaders("user-key",BuildConfig.IGDBKey)
                                                                    .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                                                                    .addStringBody(context.getString(R.string.search_screenShotTable)+gameMatchID+";")
                                                                    .setPriority(Priority.LOW).build().getAsJSONArray(new JSONArrayRequestListener() {

                                                                @Override
                                                                public void onResponse(JSONArray response) {
                                                                    JSONObject localJSON;
                                                                    String[] screenshotURLArray = new String[response.length()];
                                                                    DatabaseHelper db = new DatabaseHelper(context);
                                                                    try{
                                                                        for (int r=0; r < response.length(); r++){
                                                                            localJSON = response.optJSONObject(r);

                                                                            /**
                                                                             * left off at:
                                                                             *  -grab all the URLS, append the 'https:' and the 'T_720p'
                                                                             *  -set the arraylist of strings to the game object so we acess later
                                                                             *  -test functionality
                                                                             */
                                                                            if (localJSON.has("url")){
                                                                                final String tempUrl=localJSON.getString("url");
                                                                                final String newUrl = tempUrl.replace("thumb","720p");

                                                                                screenshotURLArray[r] = "https:"+newUrl;
                                                                            }

                                                                        }
                                                                        //System.out.println(screenshotURLArray.length);


                                                                    }catch (JSONException e){
                                                                        e.printStackTrace();
                                                                        e.getCause();
                                                                    }
                                                                    release_game.setGameScreenshots(screenshotURLArray);
                                                                    arrayList.add(release_game);
                                                                    db.addGame(release_game);
                                                                    customHomeAdapterClass.notifyDataSetChanged();
                                                                    progressDialog.dismiss();

                                                                    db.close();
                                                                }

                                                                @Override
                                                                public void onError(ANError anError) {

                                                                }
                                                            });
                                                        }

                                                    }
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
