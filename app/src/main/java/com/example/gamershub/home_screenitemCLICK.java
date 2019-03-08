package com.example.gamershub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gamershub.objectPackage.gameHome;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link home_screenitemCLICK.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link home_screenitemCLICK#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_screenitemCLICK extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //create a new object of our 'gameHome' class so that we can use it locally
    gameHome gameHome = null;

    private OnFragmentInteractionListener mListener;


    //define the onscreenObjects
    private TextView videoGameTitle;
    private TextView videoGameDescription;
    private TextView videoGameRating;
    private TextView videoGameImageUrl;
    private TextView videoGamePlatform;
    private TextView videoGameWebUrl;
    private TextView videoGameCover;
    private TextView videoGameInitialRelease;


    public home_screenitemCLICK() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_screenitemCLICK.
     */
    // TODO: Rename and change types and number of parameters
    public static home_screenitemCLICK newInstance(String param1, String param2) {
        home_screenitemCLICK fragment = new home_screenitemCLICK();
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
        //create a new bundle and get the arguments passed from the calling method
        Bundle game = getArguments();
        //grab the serializable out of the bundle that has our key
        //set this to the gameHome object we created globally
        gameHome = (gameHome) game.getSerializable("game");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screenitem_click, container, false);

        //set the items to the ID of the object so we can have access
        videoGameTitle = view.findViewById(R.id.videoGameName);
        videoGameDescription = view.findViewById(R.id.videoGameDescription);
        videoGameRating = view.findViewById(R.id.videoGameRating);
        videoGameCover = view.findViewById(R.id.videoGameCover);
        videoGameImageUrl = view.findViewById(R.id.videoGameImageURL);
        videoGameInitialRelease = view.findViewById(R.id.videoGameInitialReleaseDate);
        videoGamePlatform = view.findViewById(R.id.videoGamePlatform);
        videoGameWebUrl = view.findViewById(R.id.videoGameWebURL);


        videoGameTitle.setText(String.valueOf(gameHome.getName()));
        videoGameDescription.setText(String.valueOf(gameHome.getDescription()));
        videoGameRating.setText(String.valueOf(String.valueOf(gameHome.getRating())));
        videoGameCover.setText(String.valueOf(String.valueOf(gameHome.getGameCover())));
        videoGameImageUrl.setText(String.valueOf(gameHome.getImageViewUrl()));
        videoGameInitialRelease.setText(String.valueOf(gameHome.getReleaseDate()));
        videoGamePlatform.setText(String.valueOf(gameHome.getPlatform()));
        videoGameWebUrl.setText(String.valueOf(gameHome.getWebsiteUrl()));


        //and volla
        System.out.println("ID: "+gameHome.getId());
        System.out.println("NAME: "+gameHome.getName());
        System.out.println("DESCRIPTION: "+gameHome.getDescription());
        System.out.println("RATING: "+gameHome.getRating());
        System.out.println("IMAGEURL: "+gameHome.getImageViewUrl());
        System.out.println("PLATFORM: "+gameHome.getPlatform());
        System.out.println("WEBURL: "+gameHome.getWebsiteUrl());
        System.out.println("COVER: "+gameHome.getGameCover());
        System.out.println("RELEASEDATE: "+gameHome.getReleaseDate());
        System.out.println("COVERURL: "+gameHome.getGameCoverURL());
        System.out.println("COVERURL-WIDTH: "+gameHome.getWidth());
        System.out.println("COVERURL-HEIGHT: "+gameHome.getHeight());


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
