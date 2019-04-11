package com.example.gamershub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gamershub.Database.DatabaseHelper;
import com.example.gamershub.igdbAPI.APICOMMAND;
import com.example.gamershub.objectPackage.CustomHomeAdapterClass;
import com.example.gamershub.objectPackage.CustomPinnedAdapterClass;
import com.example.gamershub.objectPackage.commentObject;
import com.example.gamershub.objectPackage.gameHome;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pinnedgames_screen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pinnedgames_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pinnedgames_screen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Create a new instance of our APICOMMAND class
    APICOMMAND apicommand = new APICOMMAND();

    //Create a new RecyclerView so we can connect it
    private RecyclerView pinnedGames;

    //Create a new Arraylist so we can use it to hold the users pinned games
    ArrayList<gameHome> pinnedGamesList = new ArrayList<>();
    //Create a new Arraylist so that we can search our pinnedGamesList to see if we have a specific game
    ArrayList<gameHome> searchGamesList = new ArrayList<>();
    //create a fragment transaction
    FragmentManager fm;

    //create a wrap for our text input
    private TextInputLayout searchBarHolder;

    //create a edittext for the searchBox itself
    private EditText searchBox;

    //create a button so we can utilize the onclicklistiener
    private Button searchBtn;

    //Create a new CustomPinnedAdapterClass so we can re-use it throughout the code and recyclerviews if we decided to add more lateron
    private CustomPinnedAdapterClass customPinnedAdapterClass;



    private OnFragmentInteractionListener mListener;

    public pinnedgames_screen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pinnedgames_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static pinnedgames_screen newInstance(String param1, String param2) {
        pinnedgames_screen fragment = new pinnedgames_screen();
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
        View view = inflater.inflate(R.layout.fragment_pinnedgames_screen, container, false);

        searchBarHolder = view.findViewById(R.id.searchBarHolderPinned);
        searchBtn = view.findViewById(R.id.searchBtn);
        searchBox = view.findViewById(R.id.searchBar);
        pinnedGamesList = new ArrayList<>();
        searchGamesList = new ArrayList<>();

        pinnedGames = view.findViewById(R.id.pinnedGamesRecyclerView);
        pinnedGames.setNestedScrollingEnabled(false);

        //check to see if there is any objects inside our local database
        DatabaseHelper db = new DatabaseHelper(getContext());
        ArrayList<gameHome> dbTest = db.grabAllGames();

        //connect the custom adapter class to the desired arraylists
        customPinnedAdapterClass = new CustomPinnedAdapterClass(pinnedGamesList,getContext(),fm);
        //set the adapter on desired recyclerView
        pinnedGames.setAdapter(customPinnedAdapterClass);

        if (!dbTest.isEmpty()){
            if (!pinnedGamesList.isEmpty()){
                //was having problems seeing data after it had been updated
                pinnedGamesList = new ArrayList<>();
                apicommand.loadDataFromLocalPinnedGames(getContext(),db,customPinnedAdapterClass,pinnedGamesList,dbTest);
            }else{
                apicommand.loadDataFromLocalPinnedGames(getContext(),db,customPinnedAdapterClass,pinnedGamesList,dbTest);
            }
        }
        db.close();



        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String box = null;
                box = String.valueOf(s);
                if (String.valueOf(s).isEmpty()){
                    customPinnedAdapterClass = new CustomPinnedAdapterClass(pinnedGamesList,getContext(),fm);
                    pinnedGames.setAdapter(customPinnedAdapterClass);
                    customPinnedAdapterClass.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchGamesList = new ArrayList<>();
                String searchQuery = null;
                if (searchBarHolder.getEditText().getText()!=null){
                    //System.out.println(searchBarHolder.getEditText().getText());
                    if (!pinnedGamesList.isEmpty()){
                        for (int i=0; i<pinnedGamesList.size();i++){
                            //System.out.println(pinnedGamesList.get(i).getName());
                            if (pinnedGamesList.get(i).getName().toLowerCase().contains(String.valueOf(searchBarHolder.getEditText().getText()).toLowerCase().trim())){
                                //System.out.println("WE GOT A MATCH BOI");
                                searchGamesList.add(pinnedGamesList.get(i));
                            }
                            if (i == pinnedGamesList.size() - 1){
                                customPinnedAdapterClass = new CustomPinnedAdapterClass(searchGamesList,getContext(),fm);
                                pinnedGames.setAdapter(customPinnedAdapterClass);
                                customPinnedAdapterClass.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });


        //set the orientation of the list on load
        pinnedGames.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
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
