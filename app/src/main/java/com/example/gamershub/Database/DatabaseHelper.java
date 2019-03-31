package com.example.gamershub.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gamershub.objectPackage.commentObject;
import com.example.gamershub.objectPackage.gameHome;

import java.util.ArrayList;
import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DBVER);
    }


    //declare the version of the database
    public static final int DBVER = 1;

    //create our table names
    public static final String DATABASE_NAME = "GamersHub";
    public static final String GAME_TABLE = "ALLGAMES";
    public static final String USRREVIEW_TABLE = "userReview";
    /**
     * CREATE THE STRINGS THAT WILL REPRESENT OUR COLUMN
     */

    //the ID column will represent the DB id
    public static final String ID_COLUMN = "id";
    //the gameID column will represent the gameID
    public static final String GAMEID_COLUMN = "gameID";
    //the title column will represent the game title
    public static final String TITLE_COLUMN = "title";
    //the description column will represent the description
    public static final String DESCRIPTION_COLUMN = "description";
    //the price column will represent the game price
    public static final String PRICE_COLUMN = "price";
    //the webURL column will represent the game webURL
    public static final String WEBURL_COLUMN = "webURL";
    //the imageURL column will represent the game imageURL
    public static final String IMAGEURL_COLUMN = "imageURL";
    //the rating column will represent the game rating
    public static final String RATING_COLUMN = "rating";
    //the agger_rating column will represent the game agger_rating
    public static final String AGGERRATING_COLUMN = "agger_rating";
    //the total_rating column will represent the game total_rating
    public static final String TOTALRATING_COLUMN = "total_rating";
    //the cover column will represent the game cover
    public static final String COVERID_COLUMN = "cover";
    //the platform column will represent the game platform
    public static final String PLATFORM_COLUMN = "platform";
    //the release_date column will represent the game release_date
    public static final String RELEASE_DATE_COLUMN = "release_date";
    //the coverURL column will represent the game coverURL
    public static final String COVERURL_COLUMN = "coverURL";
    //the screenshotURL column will represent the game screenshotURL
    public static final String SCREENSHOTURL_COLUMN = "screenshotURL";
    //the timestamp column will represent the game timestamp
    public static final String TIMESTAMP_COLUMN = "timestamp";
    //the pinned column will represent if the game is pinned or not
    public static final String PINNED_COLUMN = "pinned";
    //the topic column will represent the recyclerview this data should belong too if it gets loaded in
    public static final String TOPIC_COLUMN = "topic";
    public static final String STORYLINE_COLUMN = "storyline";


    /**
     * CREATE THE TABLE 'GAME COUNT' INFO
     */

    public static final String COUNT_COLUMN = "count";
    public static final String GAMECOUNT_TABLE = "GAMECOUNT";


    /**
     * CREATE THE TABLE FOR REVIEW COMMENTS TO A SPECIFIC ITEM
     */
    //ID and gameID are borrowed from above declared psf's
    public static final String CATEGORY_COLUMN = "category";
    public static final String CONTENT_COLUMN = "content";
    public static final String CREATEDAT_COLUMN = "created_at";
    public static final String UPDATEDAT_COLUMN = "updated_at";
    public static final String REVIEWLIKES_COLUMN = "feedLikes";
    public static final String USER_COLUMN = "userID";



    /**
     * Create the 'Create Table' querys
     */
    public static final String CREATE_GAME = "CREATE TABLE " +
            GAME_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY,"
            + GAMEID_COLUMN + " INTEGER, " + TITLE_COLUMN + " TEXT, " + DESCRIPTION_COLUMN + " TEXT,"
            + STORYLINE_COLUMN + " TEXT, " + PRICE_COLUMN + " DOUBLE, " + WEBURL_COLUMN + " TEXT, " + IMAGEURL_COLUMN + " TEXT, " + RATING_COLUMN + " DOUBLE," +
            AGGERRATING_COLUMN + " DOUBLE," + TOTALRATING_COLUMN + " DOUBLE, " + COVERID_COLUMN + " INTEGER, " + PLATFORM_COLUMN
            + " TEXT, " + RELEASE_DATE_COLUMN + " TEXT, " + PINNED_COLUMN + " VARCHAR, " + SCREENSHOTURL_COLUMN + " TEXT, "+ TOPIC_COLUMN + " TEXT, "
            + COVERURL_COLUMN + " TEXT, " + TIMESTAMP_COLUMN + " TEXT "+ ")";

    public static final String CREATE_TOTAL_GAME_COUNT_TABLE = "CREATE TABLE " +  GAMECOUNT_TABLE  + "(" + COUNT_COLUMN + " TEXT, " + TIMESTAMP_COLUMN + " TEXT " + ")";

    public static final String CREATE_USER_REVIEW_TABLE = "CREATE TABLE " + USRREVIEW_TABLE + "(" + GAMEID_COLUMN + " INTEGER, " + ID_COLUMN + " INTEGER, " +
    CATEGORY_COLUMN + " INTEGER, " + CONTENT_COLUMN + " TEXT, " + CREATEDAT_COLUMN + " TEXT, " +  UPDATEDAT_COLUMN + " TEXT, " + REVIEWLIKES_COLUMN + " TEXT, " + USER_COLUMN + " TEXT "+")";


    /**
     * Create the CRUD methods
     */
    //create
    public void addGame(gameHome game){
        //grab the current database on this phone
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(GAMEID_COLUMN, game.getId());
        val.put(TITLE_COLUMN, game.getName());
        val.put(DESCRIPTION_COLUMN, game.getDescription());
        val.put(STORYLINE_COLUMN,game.getSummary());
        val.put(PRICE_COLUMN, game.getPrice());
        val.put(WEBURL_COLUMN, game.getWebsiteUrl());
        val.put(IMAGEURL_COLUMN, game.getImageViewUrl());
        val.put(RATING_COLUMN, game.getRating());
        val.put(AGGERRATING_COLUMN, game.getAggervatedRating());
        val.put(TOTALRATING_COLUMN, game.getTotalRating());
        val.put(COVERID_COLUMN, game.getGameCover());
        val.put(COVERURL_COLUMN, game.getGameCoverURL());
        val.put(PLATFORM_COLUMN, game.getPlatformsTest());
        val.put(RELEASE_DATE_COLUMN, game.getReleaseDate());
        val.put(SCREENSHOTURL_COLUMN, game.setGameScreenFromURLStringArray());
        val.put(PINNED_COLUMN,game.getIspinned());
        val.put(TIMESTAMP_COLUMN, game.getTimestamp());
        val.put(TOPIC_COLUMN, game.getRecyclerviewTopic());
        db.insert(GAME_TABLE, null, val);
        db.close();
    }

    //read
    public ArrayList<gameHome> grabAllGames(){
        ArrayList<gameHome> allGames = new ArrayList<>();
        gameHome game = null;
        String query = "SELECT * FROM " + GAME_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor pointer = db.rawQuery(query, null);
        if(pointer.moveToFirst()){
            do{
                game = new gameHome();
                game.setLocalDBID(pointer.getInt(pointer.getColumnIndex(ID_COLUMN)));
                game.setId(pointer.getInt(pointer.getColumnIndex(GAMEID_COLUMN)));
                game.setName(pointer.getString(pointer.getColumnIndex(TITLE_COLUMN)));
                game.setDescription(pointer.getString(pointer.getColumnIndex(DESCRIPTION_COLUMN)));
                game.setSummary(pointer.getString(pointer.getColumnIndex(STORYLINE_COLUMN)));
                game.setWebsiteUrl(pointer.getString(pointer.getColumnIndex(WEBURL_COLUMN)));
                game.setImageViewUrl(pointer.getString(pointer.getColumnIndex(IMAGEURL_COLUMN)));
                game.setRating(pointer.getDouble(pointer.getColumnIndex(RATING_COLUMN)));
                game.setAggervatedRating(pointer.getDouble(pointer.getColumnIndex(AGGERRATING_COLUMN)));
                game.setTotalRating(pointer.getDouble(pointer.getColumnIndex(TOTALRATING_COLUMN)));
                game.setGameCover(pointer.getInt(pointer.getColumnIndex(COVERID_COLUMN)));
                game.setPlatformsTest(pointer.getString(pointer.getColumnIndex(PLATFORM_COLUMN)));
                game.setReleaseDate(pointer.getString(pointer.getColumnIndex(RELEASE_DATE_COLUMN)));
                game.setGameCoverURL(pointer.getString(pointer.getColumnIndex(COVERURL_COLUMN)));
                game.setGameScreenshotExtendedURL(pointer.getString(pointer.getColumnIndex(SCREENSHOTURL_COLUMN)));
                game.setIspinned(pointer.getString(pointer.getColumnIndex(PINNED_COLUMN)));
                game.setTimestamp(pointer.getString(pointer.getColumnIndex(TIMESTAMP_COLUMN)));
                game.setRecyclerviewTopic(pointer.getString(pointer.getColumnIndex(TOPIC_COLUMN)));
                allGames.add(game);
            }while(pointer.moveToNext());
        }
        pointer.close();
        db.close();
        return allGames;
    }

    public gameHome grabSingleGame(int id){
        gameHome game = new gameHome();
        String query = "SELECT * FROM " + GAME_TABLE + " WHERE "+GAMEID_COLUMN+" = "+id+";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor pointer = db.rawQuery(query, null);
        if(pointer.moveToFirst()){
            do{
                game = new gameHome();
                game.setLocalDBID(pointer.getInt(pointer.getColumnIndex(ID_COLUMN)));
                game.setId(pointer.getInt(pointer.getColumnIndex(GAMEID_COLUMN)));
                game.setName(pointer.getString(pointer.getColumnIndex(TITLE_COLUMN)));
                game.setDescription(pointer.getString(pointer.getColumnIndex(DESCRIPTION_COLUMN)));
                game.setSummary(pointer.getString(pointer.getColumnIndex(STORYLINE_COLUMN)));
                game.setWebsiteUrl(pointer.getString(pointer.getColumnIndex(WEBURL_COLUMN)));
                game.setImageViewUrl(pointer.getString(pointer.getColumnIndex(IMAGEURL_COLUMN)));
                game.setRating(pointer.getDouble(pointer.getColumnIndex(RATING_COLUMN)));
                game.setAggervatedRating(pointer.getDouble(pointer.getColumnIndex(AGGERRATING_COLUMN)));
                game.setTotalRating(pointer.getDouble(pointer.getColumnIndex(TOTALRATING_COLUMN)));
                game.setGameCover(pointer.getInt(pointer.getColumnIndex(COVERID_COLUMN)));
                game.setPlatformsTest(pointer.getString(pointer.getColumnIndex(PLATFORM_COLUMN)));
                game.setReleaseDate(pointer.getString(pointer.getColumnIndex(RELEASE_DATE_COLUMN)));
                game.setGameCoverURL(pointer.getString(pointer.getColumnIndex(COVERURL_COLUMN)));
                game.setGameScreenshotExtendedURL(pointer.getString(pointer.getColumnIndex(SCREENSHOTURL_COLUMN)));
                game.setIspinned(pointer.getString(pointer.getColumnIndex(PINNED_COLUMN)));
                game.setTimestamp(pointer.getString(pointer.getColumnIndex(TIMESTAMP_COLUMN)));
                game.setRecyclerviewTopic(pointer.getString(pointer.getColumnIndex(TOPIC_COLUMN)));
            }while(pointer.moveToNext());
        }
        pointer.close();
        db.close();


        return game;
    }

    public ArrayList<gameHome> grabAllGamesFromTopic(String destination){
        ArrayList<gameHome> allGames = new ArrayList<>();
        gameHome game = null;
        String query = "SELECT * FROM " + GAME_TABLE + " WHERE "+TOPIC_COLUMN+" LIKE '%"+destination+"%';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor pointer = db.rawQuery(query, null);
        if(pointer.moveToFirst()){
            do{
                game = new gameHome();
                game.setLocalDBID(pointer.getInt(pointer.getColumnIndex(ID_COLUMN)));
                game.setId(pointer.getInt(pointer.getColumnIndex(GAMEID_COLUMN)));
                game.setName(pointer.getString(pointer.getColumnIndex(TITLE_COLUMN)));
                game.setDescription(pointer.getString(pointer.getColumnIndex(DESCRIPTION_COLUMN)));
                game.setSummary(pointer.getString(pointer.getColumnIndex(STORYLINE_COLUMN)));
                game.setWebsiteUrl(pointer.getString(pointer.getColumnIndex(WEBURL_COLUMN)));
                game.setImageViewUrl(pointer.getString(pointer.getColumnIndex(IMAGEURL_COLUMN)));
                game.setRating(pointer.getDouble(pointer.getColumnIndex(RATING_COLUMN)));
                game.setAggervatedRating(pointer.getDouble(pointer.getColumnIndex(AGGERRATING_COLUMN)));
                game.setTotalRating(pointer.getDouble(pointer.getColumnIndex(TOTALRATING_COLUMN)));
                game.setGameCover(pointer.getInt(pointer.getColumnIndex(COVERID_COLUMN)));
                game.setPlatformsTest(pointer.getString(pointer.getColumnIndex(PLATFORM_COLUMN)));
                game.setReleaseDate(pointer.getString(pointer.getColumnIndex(RELEASE_DATE_COLUMN)));
                game.setGameCoverURL(pointer.getString(pointer.getColumnIndex(COVERURL_COLUMN)));
                game.setGameScreenshotExtendedURL(pointer.getString(pointer.getColumnIndex(SCREENSHOTURL_COLUMN)));
                game.setIspinned(pointer.getString(pointer.getColumnIndex(PINNED_COLUMN)));
                game.setTimestamp(pointer.getString(pointer.getColumnIndex(TIMESTAMP_COLUMN)));
                game.setRecyclerviewTopic(pointer.getString(pointer.getColumnIndex(TOPIC_COLUMN)));
                if (allGames.contains(game)){

                }else{
                    allGames.add(game);
                }
            }while(pointer.moveToNext());
        }
        pointer.close();
        db.close();
        return allGames;
    }


    //update


    //delete

    /**
     *
     * Custom method's for data inside our LocalDatabase
     */
    public String[] grabAllGameTitles(){
        ArrayList<gameHome> allGames = grabAllGamesFromTopic("upcomingGames");
        String[] titles = null;
        if (allGames!=null){
            titles = new String[allGames.size()];
            for (int i = 0; i < allGames.size(); i++){
                System.out.println("DATABASEHELPER@GrabAllGameTitles: "+allGames.get(i).getName());
                titles[i] = allGames.get(i).getName();
            }
        }
        return titles;
    }

    public void addToPinnedGames(int databaseGameid){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("addToPinnedGammes@Databasehelper: LocalDB ID - "+databaseGameid);
        ContentValues val = new ContentValues();

        val.put(PINNED_COLUMN, "yes");
        db.update(GAME_TABLE,val,GAMEID_COLUMN + "=?", new String[]{String.valueOf(databaseGameid)});
    }

    public void removeFromPinnedGames(int databaseGameid){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("removeFromPinnedGames@Databasehelper: LocalDB ID - "+databaseGameid);
        ContentValues val = new ContentValues();

        val.put(PINNED_COLUMN, "no");
        db.update(GAME_TABLE,val,GAMEID_COLUMN + "=?", new String[]{String.valueOf(databaseGameid)});
    }


    //CRUD methods for the game count table

    //create
    public void addGameCount(String gameCount){
        final String currentDateTimeStamp = getDateTimeInstance().format(new Date());
        String total = null;
        if (gameCount!=null){
            total = String.valueOf(gameCount);
        }
        //grab the current database on this phone
        SQLiteDatabase db = this.getWritableDatabase();
        //System.out.println("---------------------------------------");
        //System.out.println("addGameCount@Databasehelper: "+game.getName());
        //System.out.println("---------------------------------------");
        ContentValues val = new ContentValues();
        val.put(COUNT_COLUMN, total);
        val.put(TIMESTAMP_COLUMN, currentDateTimeStamp);
        db.insert(GAMECOUNT_TABLE, null, val);
        db.close();
    }
    //read
    public String grabGameCount(){
        String totalGameCount = null;
        String query = "SELECT * FROM " + GAMECOUNT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor pointer = db.rawQuery(query, null);
        if(pointer.moveToFirst()){
            do{
                totalGameCount = pointer.getString(pointer.getColumnIndex(COUNT_COLUMN));
            }while(pointer.moveToNext());
        }
        pointer.close();
        db.close();
        return totalGameCount;
    }

    //update




    //delete


    /**
     * CREATE CRUD METHODS FOR COMMENT DB
     */

    public void addComment(commentObject comment){
        //grab the current database on this phone
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(GAMEID_COLUMN,comment.getGameID());
        val.put(ID_COLUMN,comment.getCommentID());
        val.put(USER_COLUMN,comment.getUserID());
        val.put(CATEGORY_COLUMN,comment.getCategory());
        val.put(CONTENT_COLUMN,comment.getReviewContent());
        val.put(CREATEDAT_COLUMN,comment.getCreatedAt());
        val.put(UPDATEDAT_COLUMN,comment.getUpdatedAt());
        val.put(REVIEWLIKES_COLUMN,comment.getReviewLikes());
        db.insert(USRREVIEW_TABLE, null, val);
        db.close();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GAME);
        db.execSQL(CREATE_TOTAL_GAME_COUNT_TABLE);
        db.execSQL(CREATE_USER_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
