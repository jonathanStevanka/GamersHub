package com.example.gamershub.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gamershub.objectPackage.gameHome;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DBVER);
    }

    //create the db name
    public static final String DATABASE_NAME = "GamersHub";

    //declare the version of the database
    public static final int DBVER = 1;

    //create our table names
    public static final String GAME_TABLE = "ALLGAMES";


    //create the columns for 'ALLGAMES'
    public static final String ID_COLUMN = "id";
    public static final String GAMEID_COLUMN = "gameID";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String PRICE_COLUMN = "price";
    public static final String WEBURL_COLUMN = "webURL";
    public static final String IMAGEURL_COLUMN = "imageURL";
    public static final String RATING_COLUMN = "rating";
    public static final String COVERID_COLUMN = "cover";
    public static final String PLATFORM_COLUMN = "platform";
    public static final String RELEASE_DATE_COLUMN = "release_date";
    public static final String COVERURL_COLUMN = "coverURL";
    public static final String SCREENSHOTURL_COLUMN = "screenshotURL";



    /**
     * Create the 'Create Table' querys
     */
    public static final String CREATE_GAME = "CREATE TABLE " +
            GAME_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY,"
            + GAMEID_COLUMN + " INTEGER, " + TITLE_COLUMN + " TEXT, " + DESCRIPTION_COLUMN + " TEXT,"
            + PRICE_COLUMN + " DOUBLE, " + WEBURL_COLUMN + " TEXT, " + IMAGEURL_COLUMN + " TEXT, " + RATING_COLUMN + " DOUBLE," +
            COVERID_COLUMN + " INTEGER, " + PLATFORM_COLUMN + " INTEGER, " + RELEASE_DATE_COLUMN + " TEXT, " + SCREENSHOTURL_COLUMN + " TEXT, " + COVERURL_COLUMN + ")";



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
        val.put(PRICE_COLUMN, game.getPrice());
        val.put(WEBURL_COLUMN, game.getWebsiteUrl());
        val.put(IMAGEURL_COLUMN, game.getImageViewUrl());
        val.put(RATING_COLUMN, game.getRating());
        val.put(COVERID_COLUMN, game.getGameCover());
        val.put(COVERURL_COLUMN, game.getGameCoverURL());
        val.put(PLATFORM_COLUMN, game.getPlatform());
        val.put(RELEASE_DATE_COLUMN, game.getReleaseDate());
        val.put(SCREENSHOTURL_COLUMN, game.getGameScreenshots());
        db.insert(GAME_TABLE, null, val);
        db.close();
    }

    //read
    public ArrayList<gameHome> grabAllGames(){
        ArrayList<gameHome> allGames = new ArrayList<>();
        gameHome game = new gameHome();
        String query = "SELECT * FROM " + GAME_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor pointer = db.rawQuery(query, null);
        if(pointer.moveToFirst()){
            do{


                /**
                 *
                 * @param releaseDate
                 */

                game.setId(pointer.getInt(pointer.getColumnIndex(GAMEID_COLUMN)));
                game.setName(pointer.getString(pointer.getColumnIndex(TITLE_COLUMN)));
                game.setDescription(pointer.getString(pointer.getColumnIndex(DESCRIPTION_COLUMN)));
                game.setWebsiteUrl(pointer.getString(pointer.getColumnIndex(WEBURL_COLUMN)));
                game.setImageViewUrl(pointer.getString(pointer.getColumnIndex(IMAGEURL_COLUMN)));
                game.setRating(pointer.getDouble(pointer.getColumnIndex(RATING_COLUMN)));
                game.setGameCover(pointer.getInt(pointer.getColumnIndex(COVERID_COLUMN)));
                game.setPlatform(pointer.getInt(pointer.getColumnIndex(PLATFORM_COLUMN)));
                game.setReleaseDate(pointer.getString(pointer.getColumnIndex(RELEASE_DATE_COLUMN)));
                allGames.add(game);
            }while(pointer.moveToNext());
        }
        db.close();
        return allGames;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}