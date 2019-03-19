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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.example.gamershub.Database.DatabaseHelper;
import com.example.gamershub.igdbAPI.APICOMMAND;
import com.example.gamershub.objectPackage.CustomHomeAdapterClass;
import com.example.gamershub.objectPackage.gameHome;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private RecyclerView upcoming;
    private RecyclerView popularOnPs4;
    private RecyclerView popularOnXBOX;
    private RecyclerView popularOnPC;

    //Create our arraylist's so we can access them throughout the class
    ArrayList<gameHome> trendingGames = new ArrayList<>();
    ArrayList<gameHome> upcomingGames = new ArrayList<>();
    ArrayList<gameHome> popularGamesPs4 = new ArrayList<>();
    ArrayList<gameHome> popularGamesXBOX = new ArrayList<>();
    ArrayList<gameHome> popularGamesPC = new ArrayList<>();

    //create a fragment transaction
    FragmentManager fm;

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
            trendingGames = (ArrayList<gameHome>) savedInstanceState.getSerializable("trending");
        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (trendingGames != null){
            outState.putSerializable("trending",trendingGames);
        }
        if (upcomingGames != null){
            outState.putSerializable("upcoming",upcomingGames);
        }
        if (popularGamesXBOX != null){
            outState.putSerializable("popularXBOX",popularGamesXBOX);
        }
        if (popularGamesPC != null){
            outState.putSerializable("popularPC",popularGamesPC);
        }
        if (popularGamesPs4 != null){
            outState.putSerializable("popularPS4",popularGamesPs4);
        }
    }



    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState !=null){
            trendingGames = (ArrayList<gameHome>) savedInstanceState.getSerializable("trending");
            upcomingGames = (ArrayList<gameHome>) savedInstanceState.getSerializable("upcoming");
            popularGamesXBOX = (ArrayList<gameHome>) savedInstanceState.getSerializable("popularXBOX");
            popularGamesPC = (ArrayList<gameHome>) savedInstanceState.getSerializable("popularPC");
            popularGamesPs4 = (ArrayList<gameHome>) savedInstanceState.getSerializable("popularPS4");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        //check to see if there is any objects inside our local database
        DatabaseHelper db = new DatabaseHelper(getContext());
        ArrayList<gameHome> dbTest = db.grabAllGames();


        /**
         * create the methods to load in data into each individual recyclerview where the destination column in the DB
         * matches the one that is in gameHome.getTopic() method.
         * if it equates to true then it will add that data to that particular recyclerview
         */

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
                //trendingGames = new ArrayList<>();
                //apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,trendingGames,dbTest,"trendingGames");

            }else {
                //if 'trendingGames' is empty then this method will load localdata from the phone
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,trendingGames,dbTest,"trendingGames");
            }

            if (!popularGamesPs4.isEmpty()){
                //was having problems seeing data after it had been updated
                //popularGamesPs4 = new ArrayList<>();
                //apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesPs4,dbTest,"popularGamesPs4",trendingGames,48);

            }else {
                //if 'trendingGames' is empty then this method will load localdata from the phone
                apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesPs4,dbTest,"popularGamesPs4",trendingGames,48);
            }


            if (!popularGamesXBOX.isEmpty()){
                //popularGamesXBOX = new ArrayList<>();
                //apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesXBOX,dbTest,"popularGamesXBOX",trendingGames,49);
            }else{
                apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesXBOX,dbTest,"popularGamesXBOX",trendingGames,49);

            }


            if (!popularGamesPC.isEmpty()){
                //popularGamesPC = new ArrayList<>();
                //apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesPC,dbTest,"popularGamesPC",trendingGames,6);
            }else{
                apicommand.loadDataFromPopularLocal(getContext(),db,customAdapterClass,popularGamesPC,dbTest,"popularGamesPC",trendingGames,6);

            }

            if (!upcomingGames.isEmpty()){
                //was having problems seeing data after it had been updated
                //apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingGames,dbTest,"upcomingGames");

            }else {
                apicommand.loadDataFromLocal(getContext(),db,customAdapterClass,upcomingGames,dbTest,"upcomingGames");
            }
        }
        db.close();

        /**
         * FOR TESTING PURPOSES ONLY
         * -this will load dummy data inside the recyclerviews
         */
        //apicommand.InitialLoad(trendingGames,customAdapterClass);
        //apicommand.InitialLoad(upcomingGames,customAdapterClass);
        //apicommand.InitialLoad(popularGamesPs4,customAdapterClass);
        //apicommand.InitialLoad(popularGamesXBOX,customAdapterClass);
        //apicommand.InitialLoad(popularGamesPC,customAdapterClass);
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
            apicommand.getData(getContext(),trendingGames,customAdapterClass,getString(R.string.search_trendingGames),"games",null,"trendingGames");
        }
        if (upcomingGames.isEmpty()){
            apicommand.getData(getContext(),upcomingGames,customAdapterClass,getString(R.string.search_upcomingGames),"release_dates",null,"upcomingGames");
        }
//            if (popularGamesPs4.isEmpty()){
//                apicommand.getData(getContext(),popularGamesPs4,customAdapterClass,getString(R.string.search_upcomingGamesPS4),"release_dates","PS4","popularGamesPs4");
//            }
//            if (popularGamesXBOX.isEmpty()){
//                apicommand.getData(getContext(),popularGamesXBOX,customAdapterClass,getString(R.string.search_upcomingGamesXBOX),"games","XBOX","popularGamesXBOX");
//            }
//            if (popularGamesPC.isEmpty()){
//                apicommand.getData(getContext(),popularGamesPC,customAdapterClass,getString(R.string.search_upcomingGamesPC),"games","PC","popularGamesPC");
//            }

        System.out.println("trendingGames SIZE: "+trendingGames.size());
        System.out.println("upcomingGames SIZE: "+upcomingGames.size());
        System.out.println("popularGamesPs4 SIZE: "+popularGamesPs4.size());
        System.out.println("popularGamesXBOX SIZE: "+popularGamesXBOX.size());
        System.out.println("popularGamesPC SIZE: "+popularGamesPC.size());

        //working
        //System.out.println("test1");


        //set the layoutManager on all recyclerViews and set them to horizontal
        trending.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        upcoming.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularOnPs4.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularOnXBOX.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularOnPC.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

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
