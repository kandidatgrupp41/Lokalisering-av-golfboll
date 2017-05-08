package com.devace.johannesberghult.blutoothapi21test.Fragments;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devace.johannesberghult.blutoothapi21test.Activities.MainActivity;
import com.devace.johannesberghult.blutoothapi21test.Misc.Device;
import com.devace.johannesberghult.blutoothapi21test.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.MainFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainFragmentInteractionListener mListener;

    private List<Device> foundDevices;

    private ImageButton addButton;
    private ImageView mainBackground;

    private BluetoothManager manager;
    private BluetoothAdapter bluetoothAdapter;

    private TextView golfBall1, golfBall2, golfBall3, golfBall4,golfBall5;

   private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION =1;

    public MainFragment() {
        // Required empty public constructor
    }

    public void setDevice(List<Device> devices){
        foundDevices = devices;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        manager = (BluetoothManager) getActivity().getSystemService(getActivity().BLUETOOTH_SERVICE);
        bluetoothAdapter = manager.getAdapter();
        if(foundDevices==null) {
            foundDevices = new ArrayList<Device>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        golfBall1 = (TextView) view.findViewById(R.id.golfball1_text);
        golfBall2 = (TextView) view.findViewById(R.id.golfball2_text);
        golfBall3 = (TextView) view.findViewById(R.id.golfball3_text);
        golfBall4 = (TextView) view.findViewById(R.id.golfball4_text);
        golfBall5 = (TextView) view.findViewById(R.id.golfball5_text);

        golfBall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(golfBall1.getText()!=""){
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.loadFindBallFragment(foundDevices.get(0));
                }
            }
        });
        golfBall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(golfBall2.getText()!=""){
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.loadFindBallFragment(foundDevices.get(1));
                }
            }
        });
        golfBall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(golfBall3.getText()!=""){
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.loadFindBallFragment(foundDevices.get(2));
                }
            }
        });
        golfBall4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(golfBall4.getText()!=""){
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.loadFindBallFragment(foundDevices.get(3));
                }
            }
        });
        golfBall5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(golfBall5.getText()!=""){
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.loadFindBallFragment(foundDevices.get(4));
                }
            }
        });

        addButton = (ImageButton) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.loadAddBallFragment(foundDevices);
            }
        });

        addButton.setAlpha(0f);

        mainBackground = (ImageView) view.findViewById(R.id.main_backgroud);
        mainBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
            if (mListener != null) {
                mListener.onMainFragmentInteraction(uri);
            }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
            getActivity().finish();
            return;
        }

        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getActivity(), "No Bluetooth LE Support.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        if (!(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);

        }
        if(foundDevices.size()>0){
            golfBall1.setText(foundDevices.get(0).getUserName());
            Log.d("test", "device in main");
        }
        if(foundDevices.size()>1){
            golfBall2.setText(foundDevices.get(1).getUserName());
        }
        if(foundDevices.size()>2){
            golfBall3.setText(foundDevices.get(2).getUserName());
        }
        if(foundDevices.size()>3){
            golfBall4.setText(foundDevices.get(3).getUserName());
        }
        if(foundDevices.size()>4){
            golfBall5.setText(foundDevices.get(4).getUserName());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainFragmentInteractionListener) {
            mListener = (MainFragmentInteractionListener) context;
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
    public interface MainFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMainFragmentInteraction(Uri uri);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(getActivity(), "Give fine location access", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
