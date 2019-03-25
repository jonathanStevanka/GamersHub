package com.example.gamershub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.gamershub.Database.DatabaseHelper;
import com.example.gamershub.igdbAPI.APICOMMAND;
import com.example.gamershub.objectPackage.gameHome;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;


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
    private TextView videoGameSummary;
    private TextView videoGamePlatform;
    private TextView videoGameWebUrl;
    private TextView videoGameCover;
    private TextView videoGameInitialRelease;
    //onscreenLabels
    private TextView gameSummaryLabel;
    private TextView gameDescriptionLabel;
    private TextView gamePlatformsLabel;
    private TextView gameReleasedateLabel;

    boolean isPinned;


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
        gameHome = (gameHome) game.getParcelable("game");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screenitem_click, container, false);

        //create a connection to our DatabaseHelper class
        final DatabaseHelper databaseHelper = new DatabaseHelper(getContext());



        //create connections to the images/viewpager
        ImageView imageCover = view.findViewById(R.id.gameCoverPhoto);
        final Button addToPinnedGamesBtn = view.findViewById(R.id.addToPinnedGamesBTN);
        //link the viewpager
        ViewPager screenshotViewPager = view.findViewById(R.id.videoGameScreenshots);
        CircleProgressBar ratingOverall = view.findViewById(R.id.rating);
        CircleProgressBar aggervatedRating = view.findViewById(R.id.ratingAggervated);
        CircleProgressBar totalRating = view.findViewById(R.id.totalRating);

        final DotsIndicator dotsIndicator = (DotsIndicator) view.findViewById(R.id.screenshotIndicator);

        //set the items to the ID of the object so we can have access
        videoGameTitle = view.findViewById(R.id.videoGameName);
        videoGameDescription = view.findViewById(R.id.videoGameDescription);
        videoGameSummary = view.findViewById(R.id.videoGameSummary);
        videoGameCover = view.findViewById(R.id.videoGameCover);
        videoGameInitialRelease = view.findViewById(R.id.videoGameInitialReleaseDate);
        videoGamePlatform = view.findViewById(R.id.videoGamePlatform);
        videoGameWebUrl = view.findViewById(R.id.videoGameWebURL);

        //connect the labels
        gameDescriptionLabel = view.findViewById(R.id.gameLabelDescription);
        gameSummaryLabel = view.findViewById(R.id.gameLabelSummary);
        gamePlatformsLabel = view.findViewById(R.id.gameLabelPlatforms);
        gameReleasedateLabel = view.findViewById(R.id.gameLabelReleaseDate);



        Picasso.get().load(gameHome.getGameCoverURL()).into(imageCover);
        videoGameTitle.setText(String.valueOf(gameHome.getName()));
        videoGameDescription.setText(String.valueOf(gameHome.getDescription()));

        if (gameHome.getSummary()==null){
            gameSummaryLabel.setVisibility(view.GONE);
            videoGameSummary.setVisibility(view.GONE);
        }else{
            videoGameSummary.setText(String.valueOf(gameHome.getSummary()));
        }

        videoGameCover.setText(String.valueOf(String.valueOf(gameHome.getGameCover())));
        videoGameInitialRelease.setText(String.valueOf(gameHome.getReleaseDate()));
        videoGamePlatform.setText(String.valueOf(gameHome.getPlatformsTest()));
        videoGameWebUrl.setText(String.valueOf(gameHome.getWebsiteUrl()));



        //and volla
        System.out.println("------------------------------------");
        System.out.println("INFORMATION FOR GAME");

        System.out.println("LOCAL DBID: "+gameHome.getLocalDBID());
        System.out.println("ID: "+gameHome.getId());
        System.out.println("NAME: "+gameHome.getName());
        System.out.println("DESCRIPTION: "+gameHome.getDescription());
        System.out.println("SUMMARY: "+gameHome.getSummary());
        System.out.println("IGDB-RATING: "+gameHome.getRating());
        System.out.println("aggervated-RATING: "+gameHome.getAggervatedRating());
        System.out.println("Total-RATING: "+gameHome.getTotalRating());
        System.out.println("IMAGEURL: "+gameHome.getImageViewUrl());
        System.out.println("PLATFORM: "+gameHome.getPlatformsTest());
        System.out.println("WEBURL: "+gameHome.getWebsiteUrl());
        System.out.println("COVER: "+gameHome.getGameCover());
        System.out.println("RELEASEDATE: "+gameHome.getReleaseDate());
        System.out.println("COVERURL: "+gameHome.getGameCoverURL());
        System.out.println("SCREENSHOTURL'S: "+gameHome.getGameScreenshotExtendedURL());
        System.out.println("RECYCLERVIEWDESTINATION: "+gameHome.getRecyclerviewTopic());
        System.out.println("GAME PINNED BY USER: "+gameHome.getIspinned());
        System.out.println("TIME OF DATA ADDED TO SYSTEM: "+gameHome.getTimestamp());
        System.out.println("TOPIC: "+gameHome.getRecyclerviewTopic());
        System.out.println("------------------------------------");
        //System.out.println("SCREENSHOT LENGTH: "+gameHome.getGameScreenshotExtendedURL());




        /**
         * the code below is for testing the 'add to pinned games button'
         * -possibly going to change to an imagebutton down the road, or both who knows.
         */

            if (gameHome.getIspinned().contains("yes")){
                addToPinnedGamesBtn.setText(getResources().getString(R.string.removePinBtnText));
            }
            if (gameHome.getIspinned().contains("no")){
                addToPinnedGamesBtn.setText(getResources().getString(R.string.addPinBtnText));
            }

        System.out.println("@!isPinned: is the game a pinned game?: "+gameHome.getIspinned());

        addToPinnedGamesBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    /**
                     * when this method is called it will search the local database for this information, and apply the
                     * 'yes' inside the 'pinned' column, then when you open up the pinned games it will show the
                     * current games you have pinned in a recyclerview that goes vertically down the phone.
                     */
                    //System.out.println("Local ID from gameID: "+databaseHelper.grabIDfromGameID(gameHome.getId()));

                    if (gameHome.getIspinned().contains("no")) {
                        databaseHelper.addToPinnedGames(gameHome.getId());
                        addToPinnedGamesBtn.setText(getResources().getString(R.string.removePinBtnText));
                        gameHome.setIspinned("yes");

                    } else if (gameHome.getIspinned().contains("yes")){
                        databaseHelper.removeFromPinnedGames(gameHome.getId());
                        addToPinnedGamesBtn.setText(getResources().getString(R.string.addPinBtnText));
                        gameHome.setIspinned("no");
                    }

                }

            });

        String[] screenshotURLS = gameHome.getGameScreenshotExtendedURL().replace("[","").replace("]","").split(", ");

        customAdapter adapter = new customAdapter(screenshotURLS,getContext());
        screenshotViewPager.setAdapter(adapter);
        dotsIndicator.setViewPager(screenshotViewPager);
        ratingOverall.setProgressFormatter(new RatingCircleFormatter());
        aggervatedRating.setProgressFormatter(new RatingCircleFormatter());
        totalRating.setProgressFormatter(new RatingCircleFormatter());
        Double rating = gameHome.getRating();
        Double aggerRating = gameHome.getAggervatedRating();
        Double totRating = gameHome.getTotalRating();
        if (rating!=null){
            ratingOverall.setProgress(rating.intValue());
        }
        if (aggerRating!=null){
            aggervatedRating.setProgress(aggerRating.intValue());
        }
        if (totRating!=null){
            totalRating.setProgress(totRating.intValue());
        }



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


    public class customAdapter extends PagerAdapter{

        //create an array of strings to hold the screenshots
        String[] screenshots = null;
        //create a context to hold the context data
        Context context;
        //create a layout inflater so we can inflate our view on the viewpager
        LayoutInflater layoutInflater;

        public customAdapter(String[] screenshots, Context context){
            this.screenshots = screenshots;
            this.context = context;
            layoutInflater = layoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return screenshots.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view.equals(o);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = layoutInflater.inflate(R.layout.screenshotlayout,container,false);

            final ImageView screenshotHolder = view.findViewById(R.id.screenshotHolder);
            Picasso.get().load(screenshots[position]).into(screenshotHolder);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    public static final class RatingCircleFormatter implements CircleProgressBar.ProgressFormatter{
        private static final String DEFAULT_PATTERN = "%d%%";

        @Override
        public CharSequence format(int progress, int max) {
            return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
        }
    }

}
