package com.example.gamershub;

import java.sql.Timestamp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.gamershub.Database.DatabaseHelper;
import com.example.gamershub.igdbAPI.APICOMMAND;
import com.example.gamershub.objectPackage.CustomHomeAdapterClass;
import com.example.gamershub.objectPackage.commentObject;
import com.example.gamershub.objectPackage.gameHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static java.text.DateFormat.getDateInstance;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeScreen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeScreen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    //----------------------------------------------------
    //Create a new instance of our APICOMMAND class
    APICOMMAND apicommand = new APICOMMAND();

    //Connect our recyclerviews so we can access them throughout the class
    private RecyclerView trending;
    private RecyclerView recentlySearched;
    private RecyclerView upcoming;
    private RecyclerView upcomingOnPS4;
    private RecyclerView upcomingOnXBOX;
    private RecyclerView upcomingOnPC;
    private RecyclerView popularOnPs4;
    private RecyclerView popularOnXBOX;
    private RecyclerView popularOnPC;

    private TextView recentlySearcheedGamesLabel;

    //Create our arraylist's so we can access them throughout the class
    ArrayList<gameHome> trendingGames = new ArrayList<>();
    ArrayList<gameHome> recentlySearchedList = new ArrayList<>();
    ArrayList<gameHome> popularGamesPs4 = new ArrayList<>();
    ArrayList<gameHome> popularGamesXBOX = new ArrayList<>();
    ArrayList<gameHome> popularGamesPC = new ArrayList<>();

    ArrayList<gameHome> upcomingGames = new ArrayList<>();
    ArrayList<gameHome> upcomingPS4 = new ArrayList<>();
    ArrayList<gameHome> upcomingXBOX = new ArrayList<>();
    ArrayList<gameHome> upcomingPC = new ArrayList<>();

    //testing arraylist for comments
    ArrayList<commentObject> comments = new ArrayList<>();

    //create a fragment transaction
    FragmentManager fm;

    //gesturecontrols
    SwipeRefreshLayout homeRefreshLayout;

    //Create an instance of our 'CustomHomeAdapterClass'
    private CustomHomeAdapterClass customAdapterClass;


    //----------------------------------------------------
    public HomeScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeScreen newInstance(String param1, String param2) {
        HomeScreen fragment = new HomeScreen();
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
            //trendingGames =  savedInstanceState.getParcelableArrayList("trending");
            upcomingGames = savedInstanceState.getParcelableArrayList("upcoming");
            popularGamesXBOX = savedInstanceState.getParcelableArrayList("popularXBOX");
            popularGamesPC =  savedInstanceState.getParcelableArrayList("popularPC");
            popularGamesPs4 =  savedInstanceState.getParcelableArrayList("popularPS4");
        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

//        if (trendingGames != null){
//            outState.putParcelableArrayList("trending",trendingGames);
//        }

        if (upcomingGames != null){
            outState.putParcelableArrayList("upcoming",upcomingGames);
        }
        if (popularGamesXBOX != null){
            outState.putParcelableArrayList("popularXBOX",popularGamesXBOX);
        }
        if (popularGamesPC != null){
            outState.putParcelableArrayList("popularPC",popularGamesPC);
        }
        if (popularGamesPs4 != null){
            outState.putParcelableArrayList("popularPS4",popularGamesPs4);
        }

    }



    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState !=null){
            //trendingGames = savedInstanceState.getParcelableArrayList("trending");
            upcomingGames = savedInstanceState.getParcelableArrayList("upcoming");
            popularGamesXBOX = savedInstanceState.getParcelableArrayList("popularXBOX");
            popularGamesPC = savedInstanceState.getParcelableArrayList("popularPC");
            popularGamesPs4 = savedInstanceState.getParcelableArrayList("popularPS4");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        //connect the trending recyclerview
        trending = view.findViewById(R.id.trendingGamesALL);
        //connect the upcoming recyclerview
        upcoming = view.findViewById(R.id.upcomingGamesALL);
        //connect the popularOnPs4 recyclerview
        popularOnPs4 = view.findViewById(R.id.popularPS4);
        //connect the popularOnXBOX recyclerview
        popularOnXBOX = view.findViewById(R.id.popularXBOX);
        //connect the popularOnPC recyclerview
        popularOnPC = view.findViewById(R.id.popularPC);
        //connect the recently searched recyclerview so we can utilize it
        recentlySearched = view.findViewById(R.id.recentlySearchedGames);
        //connect the upcomingOnps4 recyclerview
        upcomingOnPS4 = view.findViewById(R.id.upcomingPS4);
        //connect the upcomingOnps4 recyclerview
        upcomingOnPC = view.findViewById(R.id.upcomingPC);
        //connect the upcomingOnps4 recyclerview
        upcomingOnXBOX = view.findViewById(R.id.upcomingXBOX);


        //connect the label to the recyclerview so that we can toggle if it is hidden or not
        recentlySearcheedGamesLabel = view.findViewById(R.id.row1title2_5);

        /**
         * Connect the customadapterclass we made to each recyclerview that we have
         */

        //connect the custom adapter class to the desired arraylists
        customAdapterClass = new CustomHomeAdapterClass(trendingGames,getContext(),fm);
        //set the adapter on desired recyclerView
        trending.setAdapter(customAdapterClass);

        //connect the custom adapter class to the desired arraylists
        customAdapterClass = new CustomHomeAdapterClass(upcomingGames,getContext(),fm);
        //set the adapter on desired recyclerView
        upcoming.setAdapter(customAdapterClass);

        //connect the custom adapter class to the desired arraylists
        customAdapterClass = new CustomHomeAdapterClass(popularGamesPs4,getContext(),fm);
        //set the adapter on desired recyclerView
        popularOnPs4.setAdapter(customAdapterClass);

        //connect the custom adapter class to the desired arraylists
        customAdapterClass = new CustomHomeAdapterClass(popularGamesXBOX,getContext(),fm);
        //set the adapter on desired recyclerView
        popularOnXBOX.setAdapter(customAdapterClass);

        //connect the custom adapter class to the desired arraylists
        customAdapterClass = new CustomHomeAdapterClass(popularGamesPC,getContext(),fm);
        //set the adapter on desired recyclerView
        popularOnPC.setAdapter(customAdapterClass);

        //connect the custom adapter class to the desired arraylists
        customAdapterClass = new CustomHomeAdapterClass(recentlySearchedList,getContext(),fm);
        //set the adapter on desired recyclerView
        recentlySearched.setAdapter(customAdapterClass);

        //connect the custom adapter class to the desired arraylists
        customAdapterClass = new CustomHomeAdapterClass(upcomingPC,getContext(),fm);
        //set the adapter on desired recyclerView
        upcomingOnPC.setAdapter(customAdapterClass);

        //connect the custom adapter class to the desired arraylists
        customAdapterClass = new CustomHomeAdapterClass(upcomingPS4,getContext(),fm);
        //set the adapter on desired recyclerView
        upcomingOnPS4.setAdapter(customAdapterClass);

        //connect the custom adapter class to the desired arraylists
        customAdapterClass = new CustomHomeAdapterClass(upcomingXBOX,getContext(),fm);
        //set the adapter on desired recyclerView
        upcomingOnXBOX.setAdapter(customAdapterClass);


        //add a swipe gesture to this fragment
        homeRefreshLayout = view.findViewById(R.id.refreshLayoutContainer);
        homeRefreshLayout.setDistanceToTriggerSync(200);


        //check to see if there is any objects inside our local database
        DatabaseHelper db = new DatabaseHelper(getContext());
        ArrayList<gameHome> dbTest = db.grabAllGames();

        //System.out.println("DATABASE SIZE: "+dbTest.size());
        /**
         * create the methods to load in data into each individual recyclerview where the destination column in the DB
         * matches the one that is in gameHome.getTopic() method.
         * if it equates to true then it will add that data to that particular recyclerview
         */

        //make a check here too see if Recently searched is empty or not
        if (recentlySearchedList.isEmpty()){
            recentlySearched.setVisibility(view.GONE);
            recentlySearcheedGamesLabel.setVisibility(view.GONE);
            //System.out.println("RECENTLY SEARCHED IS EMPTY");
        }

        //if the database is not empty, load objects from the DB into our recyclerviews
        if (!dbTest.isEmpty()){

            if (!trendingGames.isEmpty()){
                //On the inital load of the app AFTER it has been launched before to add data from API into the database.
                //this should always be FALSE.
                //UNLESS
                //the phone rotates the output will be TRUE
                //and we are covered because we added the arraylist data to our onsaveinstance state so we can
                //return everything back to how we wanted it

                //was having problems seeing data after it had been updated
                trendingGames = new ArrayList<>();
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,trendingGames,dbTest,"trendingGames");

            }else {
                //if 'trendingGames' is empty then this method will load localdata from the phone
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,trendingGames,dbTest,"trendingGames");
            }


            if (!popularGamesPs4.isEmpty()){
                //was having problems seeing data after it had been updated
                popularGamesPs4 = new ArrayList<>();
                apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesPs4,dbTest,"popularGamesPs4",trendingGames,48);
            }else {
                //if 'trendingGames' is empty then this method will load localdata from the phone
                apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesPs4,dbTest,"popularGamesPs4",trendingGames,48);
            }


            if (!recentlySearchedList.isEmpty()) {
                //was having problems seeing data after it had been updated
                recentlySearchedList = new ArrayList<>();
                apicommand.loadDataFromLocal(getContext(), db, customAdapterClass, recentlySearchedList, dbTest, "searchGamesScreen");
            }else{
                apicommand.loadDataFromLocal(getContext(), db, customAdapterClass, recentlySearchedList, dbTest, "searchGamesScreen");
            }

            if (!popularGamesXBOX.isEmpty()){
                popularGamesXBOX = new ArrayList<>();
                apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesXBOX,dbTest,"popularGamesXBOX",trendingGames,49);
            }else{
                apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesXBOX,dbTest,"popularGamesXBOX",trendingGames,49);
            }


            if (!popularGamesPC.isEmpty()){
                popularGamesPC = new ArrayList<>();
                apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesPC,dbTest,"popularGamesPC",trendingGames,6);
            }else{
                apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesPC,dbTest,"popularGamesPC",trendingGames,6);

            }

            if (!upcomingGames.isEmpty()){
                //was having problems seeing data after it had been updated
                upcomingGames = new ArrayList<>();
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingGames,dbTest,"upcomingGames");

            }else {
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingGames,dbTest,"upcomingGames");
            }

            if (!upcomingPC.isEmpty()){
                //was having problems seeing data after it had been updated
                upcomingPC = new ArrayList<>();
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingPC,dbTest,"upcomingOnPC");

            }else {
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingPC,dbTest,"upcomingOnPC");
            }

            if (!upcomingPS4.isEmpty()){
                //was having problems seeing data after it had been updated
                upcomingPS4 = new ArrayList<>();
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingPS4,dbTest,"upcomingOnPS4");

            }else {
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingPS4,dbTest,"upcomingOnPS4");
            }

            if (!upcomingXBOX.isEmpty()){
                //was having problems seeing data after it had been updated
                upcomingXBOX = new ArrayList<>();
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingXBOX,dbTest,"upcomingOnXBOX");

            }else {
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingXBOX,dbTest,"upcomingOnXBOX");
            }

            //make a check here too see if Recently searched is empty or not
            if (!recentlySearchedList.isEmpty()){
                recentlySearched.setVisibility(view.VISIBLE);
                recentlySearcheedGamesLabel.setVisibility(view.VISIBLE);
                //System.out.println("RECENTLY SEARCHED IS NOT EMPTY");
            }

            comments = db.grabAllComments();
        }
        db.close();


        /**
         * This is where the action happens
         * Please check the params on the 'getData()' function
         * The getData() method will grab the data and programmatically add it to each recyclerview inside the application.
         *
         * WE SHOULD ALSO BE CHECKING TO SEE IF THERE IS ANY SAVED DATA ON THE DEVICE, IF THERE IS
         * PULL IT IN INSTEAD OF USING OUR API TO PULL REQUESTS
         * SHOULD HELP ON KEEPING THE API PULLS DOWN
         */

        if (trendingGames.isEmpty()){
            //apicommand.getData(getContext(),trendingGames,customAdapterClass,getString(R.string.search_trendingGames),"games",null,"trendingGames",popularGamesPs4,popularGamesXBOX,popularGamesPC);
            apicommand.getData(getContext(),trendingGames,customAdapterClass,getString(R.string.search_trendingGames),"games",null,"trendingGames",popularGamesPs4,popularGamesXBOX,popularGamesPC);
        }
        if (upcomingGames.isEmpty()){
            //apicommand.getData(getContext(),upcomingGames,customAdapterClass,getString(R.string.search_upcomingGames),"release_dates",null,"upcomingGames",popularGamesPs4,popularGamesXBOX,popularGamesPC);
            apicommand.getData(getContext(),upcomingGames,customAdapterClass,getString(R.string.search_upcomingGames),"release_dates",null,"upcomingGames",upcomingPS4,upcomingXBOX,upcomingPC);
        }


        homeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * this OnRefreshListener will update all games on the homescreen and insert accordingly.
                 */
                if (!trendingGames.isEmpty()){
                    String[] usedID = new String[trendingGames.size()];
                    String[] currentID = new String[10];
                    int counter = 0;
                    for (int i=0; i<trendingGames.size();i++){
                        System.out.println(trendingGames.get(i).getId());
                        currentID[counter] = String.valueOf(trendingGames.get(i).getId());
                        counter++;
                        usedID[i] = String.valueOf(trendingGames.get(i).getId());
                        if (counter==10){
                            System.out.println("JSON REQUEST FIRED");
                            counter=0;

                            System.out.println(Arrays.toString(usedID));
                            System.out.println(Arrays.toString(currentID));

                            String cleanedCurrentID = Arrays.toString(currentID);
                            cleanedCurrentID = cleanedCurrentID.substring(1,cleanedCurrentID.length()-1).replace(" ","").trim();

                            System.out.println(cleanedCurrentID);

                            currentID = new String[10];



                            System.out.println(getString(R.string.update_allGames) +" where id = ("+cleanedCurrentID+");");

//                            AndroidNetworking.post("https://api-v3.igdb.com/games/").addHeaders("user-key",BuildConfig.IGDBKey)
//                                    .addHeaders("Accept","application/json").addHeaders("Content-Type","application/x-www-form-urlencoded")
//                                    .addStringBody(getString(R.string.update_allGames)+" where id = ("+cleanedCurrentID+");")
//                                    .setPriority(Priority.LOW).build().getAsJSONArray(new JSONArrayRequestListener() {
//
//                                @Override
//                                public void onResponse(JSONArray response) {
//                                    JSONObject jsonObject = null;
//                                    if (response!=null){
//                                        for (int i=0; i<response.length();i++){
//                                            try{
//                                                jsonObject = response.getJSONObject(i);
//                                            }catch (JSONException e){
//                                                e.printStackTrace();
//                                                e.getCause();
//                                                e.getMessage();
//                                                e.getStackTrace();
//                                            }
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onError(ANError anError) {
//                                    anError.getResponse();
//                                    anError.getErrorBody();
//                                    anError.getErrorCode();
//                                    anError.getErrorDetail();
//                                }
//                            });

                        }

                    }



                    homeRefreshLayout.setRefreshing(false);
                }else{
                    //this will hide the refreshbar
                    homeRefreshLayout.setRefreshing(false);
                }

            }
        });



        trending.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                /**
                 * Create a switch statement here to cycle through the current recyclerview and get the state of what is going on
                 * -The target
                 * -----------
                 * Hide the SwipeOnRefreshListiener so that it wont be pulled down while scrolling this recyclerview
                 */


                switch (newState) {
                    case (RecyclerView.SCROLL_STATE_DRAGGING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("DRAGGING");
                        break;

                    case (RecyclerView.SCROLL_STATE_IDLE):
                        homeRefreshLayout.setEnabled(true);
                        System.out.println("IDLE");
                        break;

                    case (RecyclerView.SCROLL_STATE_SETTLING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("SETTLING");
                        break;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });




        recentlySearched.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                /**
                 * Create a switch statement here to cycle through the current recyclerview and get the state of what is going on
                 * -The target
                 * -----------
                 * Hide the SwipeOnRefreshListiener so that it wont be pulled down while scrolling this recyclerview
                 */


                switch (newState) {
                    case (RecyclerView.SCROLL_STATE_DRAGGING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("DRAGGING");
                        break;

                    case (RecyclerView.SCROLL_STATE_IDLE):
                        homeRefreshLayout.setEnabled(true);
                        System.out.println("IDLE");
                        break;

                    case (RecyclerView.SCROLL_STATE_SETTLING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("SETTLING");
                        break;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        popularOnPs4.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                /**
                 * Create a switch statement here to cycle through the current recyclerview and get the state of what is going on
                 * -The target
                 * -----------
                 * Hide the SwipeOnRefreshListiener so that it wont be pulled down while scrolling this recyclerview
                 */


                switch (newState) {
                    case (RecyclerView.SCROLL_STATE_DRAGGING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("DRAGGING");
                        break;

                    case (RecyclerView.SCROLL_STATE_IDLE):
                        homeRefreshLayout.setEnabled(true);
                        System.out.println("IDLE");
                        break;

                    case (RecyclerView.SCROLL_STATE_SETTLING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("SETTLING");
                        break;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        popularOnXBOX.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                /**
                 * Create a switch statement here to cycle through the current recyclerview and get the state of what is going on
                 * -The target
                 * -----------
                 * Hide the SwipeOnRefreshListiener so that it wont be pulled down while scrolling this recyclerview
                 */


                switch (newState) {
                    case (RecyclerView.SCROLL_STATE_DRAGGING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("DRAGGING");
                        break;

                    case (RecyclerView.SCROLL_STATE_IDLE):
                        homeRefreshLayout.setEnabled(true);
                        System.out.println("IDLE");
                        break;

                    case (RecyclerView.SCROLL_STATE_SETTLING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("SETTLING");
                        break;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        popularOnPC.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                /**
                 * Create a switch statement here to cycle through the current recyclerview and get the state of what is going on
                 * -The target
                 * -----------
                 * Hide the SwipeOnRefreshListiener so that it wont be pulled down while scrolling this recyclerview
                 */


                switch (newState) {
                    case (RecyclerView.SCROLL_STATE_DRAGGING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("DRAGGING");
                        break;

                    case (RecyclerView.SCROLL_STATE_IDLE):
                        homeRefreshLayout.setEnabled(true);
                        System.out.println("IDLE");
                        break;

                    case (RecyclerView.SCROLL_STATE_SETTLING):
                        homeRefreshLayout.setEnabled(false);
                        System.out.println("SETTLING");
                        break;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });











        //set the layoutManager on all recyclerViews and set them to horizontal
        trending.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recentlySearched.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        upcoming.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularOnPC.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularOnPs4.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularOnXBOX.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        upcomingOnPS4.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        upcomingOnPC.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        upcomingOnXBOX.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        //return the view
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
}
