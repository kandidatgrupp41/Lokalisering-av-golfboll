package com.devace.johannesberghult.blutoothapi21test.Activities;

//http://stackoverflow.com/questions/1513485/how-do-i-get-the-current-gps-location-programmatically-in-android
// https://developer.android.com/training/location/receive-location-updates.html
// https://communityhealthmaps.nlm.nih.gov/2014/07/07/how-accurate-is-the-gps-on-my-smart-phone-part-2/
// https://articles.extension.org/pages/40145/what-is-the-difference-between-a-recreational-grade-gps-a-mapping-grade-gps-and-a-survey-grade-gps

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devace.johannesberghult.blutoothapi21test.Fragments.AddBallFragment;
import com.devace.johannesberghult.blutoothapi21test.Fragments.FindBallFragment;
import com.devace.johannesberghult.blutoothapi21test.Fragments.MainFragment;
import com.devace.johannesberghult.blutoothapi21test.Fragments.NameBallFragment;
import com.devace.johannesberghult.blutoothapi21test.Misc.Device;

import com.devace.johannesberghult.blutoothapi21test.R;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentInteractionListener, AddBallFragment.AddBallInteractionListener, NameBallFragment.NameBallInteractionListener, FindBallFragment.FindBallInteractionListener {

    private List<Device> deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new MainFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    public void loadAddBallFragment(List<Device> devices){
        AddBallFragment addBallFragment = new AddBallFragment();
        addBallFragment.setDevice(devices);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addBallFragment).addToBackStack(null).commit();
    }

    public void loadMainFragment(List<Device> devices){
        MainFragment mainFragment = new MainFragment();
        mainFragment.setDevice(devices);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).addToBackStack(null).commit();
    }

    public void loadNameBallFragment(List<Device> devices){
        NameBallFragment nameBallFragment = new NameBallFragment();
        nameBallFragment.setDevice(devices);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, nameBallFragment).addToBackStack(null).commit();

    }

    public void loadFindBallFragment(Device device){
        FindBallFragment findBallFragment = new FindBallFragment();
        findBallFragment.setDevice(device);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, findBallFragment).addToBackStack(null).commit();

    }


    @Override
    public void onMainFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAddBallFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNameBallFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFindBallInteraction(Uri uri) {

    }

}
