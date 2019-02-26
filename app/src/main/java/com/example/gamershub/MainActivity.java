package com.example.gamershub;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.gamershub.igdbAPI.APICOMMAND;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity implements HomeScreen.OnFragmentInteractionListener{

    private TextView mTextMessage;

    FragmentManager fm;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = fm.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content, new HomeScreen());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.navigation_pinned:
                    return true;
                case R.id.navigation_gameTrade:
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get the supportfragmentManager for the class
        fm = getSupportFragmentManager();
        //create a new APIcommand class so we can acess our custom methods throughout the application
        APICOMMAND apicommand = new APICOMMAND();
        //initialize the AndroidNetworking API so we can have access to the POST & GET methods
        AndroidNetworking.initialize(getApplicationContext());

        //initilize the bottom view navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //set the onclick listiener for the navigationitemselected
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //create a way to make the default screen thats loaded is the home screen
        FragmentTransaction transaction = fm.beginTransaction();
        //replace the current screen
        transaction.replace(R.id.content, new HomeScreen());
        //DO NOT add to back stack null so that users cannot go backwards to an empty screen
        //Commit the transaction and make the change to the screen
        transaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
