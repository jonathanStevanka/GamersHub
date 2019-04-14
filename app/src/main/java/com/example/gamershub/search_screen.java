package com.example.gamershub;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.gamershub.Database.DatabaseHelper;
import com.example.gamershub.objectPackage.CustomPinnedAdapterClass;
import com.example.gamershub.objectPackage.commentObject;
import com.example.gamershub.objectPackage.gameHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link search_screen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link search_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search_screen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextInputLayout searchBarHolder;
    private TextInputEditText searchBar;
    private Button searchBtn;
    private ArrayList<gameHome> searchedGames = new ArrayList<>();

    //Create a new CustomPinnedAdapterClass so we can re-use it throughout the code and recyclerviews if we decided to add more lateron
    private CustomPinnedAdapterClass customPinnedAdapterClass;


    RecyclerView searchedGame;
    FragmentManager fm;

    public search_screen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static search_screen newInstance(String param1, String param2) {
        search_screen fragment = new search_screen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        fm = getActivity().getSupportFragmentManager();

        if (savedInstanceState != null){
            searchedGames = (ArrayList<gameHome>) savedInstanceState.getSerializable("searchedGames");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (searchedGames != null){
            outState.putSerializable("searchedGames",searchedGames);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_screen, container, false);

        searchBarHolder = view.findViewById(R.id.searchBarHolder);
        searchBar = view.findViewById(R.id.searchBar);
        searchBtn = view.findViewById(R.id.searchBtn);
        searchedGame = view.findViewById(R.id.searchedGameRecyclerView);
        searchedGame.setNestedScrollingEnabled(false);

        //connect the custom adapter class to the desired arraylists
        customPinnedAdapterClass = new CustomPinnedAdapterClass(searchedGames,getContext(),fm);
        searchedGame.setAdapter(customPinnedAdapterClass);

        final String currentDateTimeStamp = getDateTimeInstance().format(new Date());

        getTotalGameCount();


        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String searchedGame = null;
                if (searchBar.getText()!=null){


                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading Game data...please wait");
                    progressDialog.show();


                    searchedGame = searchBar.getText().toString();

                    System.out.println("search "+"\""+searchedGame+"\"; "+getString(R.string.search_pinnedGamesScreen));

                    AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
                            .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                            .addStringBody("search "+"\""+searchedGame+"\";"+getString(R.string.search_pinnedGamesScreen))
                            .setPriority(Priority.LOW).build().getAsJSONArray(new JSONArrayRequestListener() {

                        @Override
                        public void onResponse(JSONArray response) {
                            final DatabaseHelper db = new DatabaseHelper(getContext());
                            //Instantiate a new JSONObject for the response information
                            JSONObject jsonGameObject = null;
                            JSONArray jsonScreenshotArray = null;
                            JSONArray jsonReleaseObject = null;
                            JSONObject jsonCoverObject = null;
                            gameHome game;

                            try {
                                customPinnedAdapterClass.RemoveAllData();
                                for (int i=0; i<response.length();i++){
                                    game = new gameHome();

                                    game.setTimestamp(currentDateTimeStamp);
                                    jsonGameObject = response.getJSONObject(i);

                                    if (jsonGameObject.has("id")){
                                        game.setId(jsonGameObject.getInt("id"));

                                    }
                                    game.setName(jsonGameObject.getString("name"));
                                    if (jsonGameObject.has("platforms")){
                                        game.setPlatformsTest(jsonGameObject.getString("platforms"));
                                    }
                                    //popularity goes here
                                    if (jsonGameObject.has("rating")){
                                        //double gameRating = jsonGameObject.getDouble("rating");
                                        game.setRating(jsonGameObject.getDouble("rating"));
                                    }
                                    if (jsonGameObject.has("release_dates")){
                                        jsonReleaseObject = jsonGameObject.getJSONArray("release_dates");
                                        String releaseDate = jsonReleaseObject.getJSONObject(0).getString("human");
                                        game.setReleaseDate(releaseDate);
                                    }
                                    if (jsonGameObject.has("cover")){
                                        jsonCoverObject = jsonGameObject.getJSONObject("cover");
                                        game.setGameCover(jsonCoverObject.getInt("id"));
                                        game.setGameCoverURL("https:"+jsonCoverObject.getString("url").replace("thumb","720p"));
                                    }
                                    if (jsonGameObject.has("screenshots")){
                                        jsonScreenshotArray = jsonGameObject.getJSONArray("screenshots");
                                        String[] screenshotUrlsUnextended = new String[jsonScreenshotArray.length()];
                                        for (int r =0; r<jsonScreenshotArray.length();r++){
                                            screenshotUrlsUnextended[r] = "https:"+jsonScreenshotArray.getJSONObject(r).getString("url").replace("thumb","720p");
                                            System.out.println("'"+screenshotUrlsUnextended[r]+"'");
                                        }
                                        game.setGameScreenshots(screenshotUrlsUnextended);
                                    }
                                    if (jsonGameObject.has("aggregated_rating")){
                                        game.setAggervatedRating(jsonGameObject.getDouble("aggregated_rating"));
                                    }
                                    if (jsonGameObject.has("summary")){
                                        game.setDescription(jsonGameObject.getString("summary"));
                                    }
                                    if (jsonGameObject.has("total_rating")){
                                        game.setTotalRating(jsonGameObject.getDouble("total_rating"));
                                    }
                                    if (jsonGameObject.has("url")){
                                        game.setWebsiteUrl(jsonGameObject.getString("url"));

                                    }
                                    if (jsonGameObject.has("storyline")){
                                        game.setSummary(jsonGameObject.getString("storyline"));
                                    }
                                    game.setIspinned("no");
                                    game.setRecyclerviewTopic("searchGamesScreen");
                                    System.out.println("----------------------------------------------------");
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME ID - "+game.getId());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME NAME - "+game.getName());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME COVERURL - "+game.getGameCoverURL());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME PLATFORMS - "+game.getPlatformsTest());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME AGGERVATED_RATING - "+game.getAggervatedRating());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME RATING - "+game.getRating());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME TOTALRATING - "+game.getRating());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME SCREENSHOTS - "+game.getGameScreenshotExtendedURL());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME DESCRIPTION - "+game.getDescription());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME SUMMARY - "+game.getSummary());
                                    System.out.println("SEARCH_SCREEN@SEARCHBTN_ONCLICK: GAME WEBURL - "+game.getWebsiteUrl());
                                    System.out.println("----------------------------------------------------");
                                    searchedGames.add(game);
                                    customPinnedAdapterClass.notifyDataSetChanged();
                                }

                                progressDialog.dismiss();
                                db.close();
                                System.out.println(searchedGames.size());

                            }catch (JSONException e){
                                e.printStackTrace();
                                e.getCause();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            anError.getResponse();
                            anError.getErrorBody();
                            anError.getErrorCode();
                            anError.getErrorDetail();
                        }

                    });

                }

            }
        });

        searchedGame.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getTotalGameCount(){
        DatabaseHelper db = new DatabaseHelper(getContext());
        String totalGameCount = db.grabGameCount();
        if (totalGameCount!=null){

            //testing if the date is different or not
            try {
                //create a string that represents the current time
                final String currentDateTimeStamp = getDateTimeInstance().format(new Date());

                Date gameCountTimestamp = getDateTimeInstance().parse(db.grabGameCountDate());
                final String pastDateTimeStamp = getDateTimeInstance().format(gameCountTimestamp);

                Date currentDateTimeStampDate = getDateTimeInstance().parse(currentDateTimeStamp);

                System.out.println("Game timestamp: "+pastDateTimeStamp);
                System.out.println("Current timestamp: "+currentDateTimeStamp);
                if (gameCountTimestamp.getDate() != currentDateTimeStampDate.getDate()){
                    System.out.println("search_screen-LINE347: GAME TIMESTAMP IS OLDER THAN 24HRS....UPDATING TIMESTAMP...");
                    String count = db.grabGameCount();
                    //place update count method here
                    updateTotalGameCountDate();
                }else{
                    System.out.println("search_screen-LINE345: GAME TIMESTAMP IS WITHIN 24HRS AS THE CURRENT TIMESTAMP....ABORT UPDATE...");
                }
                totalGameCount = db.grabGameCount();
                searchBarHolder.setHint("Search "+totalGameCount+" Games");

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //System.out.println("GAMECOUNTPOSTDATE: "+db.grabGameCountDate());
            searchBarHolder.setHint("Search "+totalGameCount+" Games");
            db.close();
        }else{
            AndroidNetworking.post("https://api-v3.igdb.com/games/count").addHeaders("user-key",BuildConfig.IGDBKey)
                    .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                    .setPriority(Priority.LOW).build().getAsJSONObject(new JSONObjectRequestListener() {


                @Override
                public void onResponse(JSONObject response) {
                    JSONObject jsonObject = null;
                    String totalGames=null;
                    DatabaseHelper db = new DatabaseHelper(getContext());
                    if (response!=null){
                        jsonObject = response;
                        try {
                            totalGames = jsonObject.getString("count");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (totalGames!=null){
                        db.addGameCount(totalGames);
                        getTotalGameCount();
                    }
                }

                @Override
                public void onError(ANError anError) {
                    anError.getErrorCode();
                    anError.getErrorBody();
                    anError.getErrorDetail();
                    anError.getResponse();
                }


            });
        }

    }

    public void updateTotalGameCountDate(){

        AndroidNetworking.post("https://api-v3.igdb.com/games/count").addHeaders("user-key",BuildConfig.IGDBKey)
                .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
                .setPriority(Priority.LOW).build().getAsJSONObject(new JSONObjectRequestListener() {

            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject = null;
                String totalGames=null;
                DatabaseHelper db = new DatabaseHelper(getContext());
                if (response!=null){
                    jsonObject = response;
                    try {
                        totalGames = jsonObject.getString("count");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //make method to update the current value inside the database instead of adding a new row
                if (totalGames!=null){
                    db.addGameCount(totalGames);
                    db.updateGameCount(totalGames);
                }

                db.close();
            }

            @Override
            public void onError(ANError anError) {
                anError.getErrorCode();
                anError.getErrorBody();
                anError.getErrorDetail();
                anError.getResponse();
            }


        });

    }
}
