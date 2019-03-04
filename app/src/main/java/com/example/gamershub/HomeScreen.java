package com.example.gamershub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.gamershub.igdbAPI.APICOMMAND;
import com.example.gamershub.objectPackage.CustomHomeAdapterClass;
import com.example.gamershub.objectPackage.gameHome;

import java.util.ArrayList;


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
    private ArrayList<gameHome> trendingGames;
    private ArrayList<gameHome> upcomingGames;
    private ArrayList<gameHome> popularGamesPs4;
    private ArrayList<gameHome> popularGamesXBOX;
    private ArrayList<gameHome> popularGamesPC;


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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);


        //Create a connection between the 'searchBtn' and the 'searchBar'
        final Button searchBtn = view.findViewById(R.id.searchBtn);
        final EditText searchBar = view.findViewById(R.id.searchBar);

        //create an ArrayList of 'gameHome' objects
        final ArrayList<gameHome> gameObjects = new ArrayList<gameHome>();
        //create the onclickListener for the searchBtn
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //use the basic searchGame command utilizing the text input from the search bar
                //gameObjects.addAll(apicommand.dumpGameInfo(apicommand.SearchGames(searchBar.getText().toString())));
                apicommand.SearchGames(searchBar.getText().toString());
                //System.out.println(jsonObjects.size());
            }
        });

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


        //instantiate new arraylists
        trendingGames = new ArrayList<>();
        upcomingGames = new ArrayList<>();
        popularGamesPs4 = new ArrayList<>();
        popularGamesXBOX = new ArrayList<>();
        popularGamesPC = new ArrayList<>();



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

        /**
         * This is where the action happens, in order to update the recyclerviews with data we need to populate them.
         * Please check the params on the 'getData()' function
         */

        apicommand.getData(getContext(),trendingGames,customAdapterClass,getString(R.string.search_trendingGames),"games",null);
        apicommand.getData(getContext(),upcomingGames,customAdapterClass,getString(R.string.search_upcomingGames),"release_dates",null);

        //working
        //apicommand.getData(getContext(),popularGamesPs4,customAdapterClass,getString(R.string.search_trendingGames),"games","PS4");
        //apicommand.getData(getContext(),popularGamesXBOX,customAdapterClass,getString(R.string.search_trendingGames),"games","XBOX");
        //apicommand.getData(getContext(),popularGamesPC,customAdapterClass,getString(R.string.search_trendingGames),"games","PC");




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
