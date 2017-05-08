package com.devace.johannesberghult.blutoothapi21test.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


import com.devace.johannesberghult.blutoothapi21test.Activities.MainActivity;
import com.devace.johannesberghult.blutoothapi21test.Misc.Device;
import com.devace.johannesberghult.blutoothapi21test.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddBallFragment.AddBallInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddBallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBallFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AddBallInteractionListener mListener;

    private ImageView addBackground;
    private Button addBackButton;
    private ProgressBar progressBar;

    private BluetoothManager manager;
    private BluetoothAdapter bluetoothAdapter;

    private List<Device> foundDevices;

    private BluetoothLeScanner bluetoothLeScanner;


    private Device foundDevice;


    public AddBallFragment() {
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
     * @return A new instance of fragment AddBallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBallFragment newInstance(String param1, String param2) {
        AddBallFragment fragment = new AddBallFragment();
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        manager = (BluetoothManager) getActivity().getSystemService(getActivity().BLUETOOTH_SERVICE);
        bluetoothAdapter = manager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        View view = inflater.inflate(R.layout.fragment_add_ball, container, false);

        addBackground = (ImageView) view.findViewById(R.id.add_backgroud);
        addBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        addBackButton = (Button)view.findViewById(R.id.add_back_button);

        addBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        addBackButton.setAlpha(0f);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);

        return view;
    }

    private void foundCloseDevice(){
         bluetoothLeScanner.stopScan(mScanCallback);

                MainActivity mainActivity = (MainActivity)getActivity();
                foundDevices.add(foundDevice);
                mainActivity.loadNameBallFragment(foundDevices);
    }

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if(result.getRssi()>-40){
                foundDevice = new Device(result.getDevice().getName(), result.getRssi(), result.getDevice().getAddress());
                foundCloseDevice();
            }
        }


        @Override
        public void onScanFailed(int errorCode){

        }

        @Override
        public void onBatchScanResults(List<ScanResult> results){
            for(ScanResult scanResult: results){


            }

        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAddBallFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddBallInteractionListener) {
            mListener = (AddBallInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        bluetoothLeScanner.startScan(mScanCallback);
    }

    @Override
    public void onPause(){
        super.onPause();
        bluetoothLeScanner.stopScan(mScanCallback);
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
    public interface AddBallInteractionListener {
        // TODO: Update argument type and name
        void onAddBallFragmentInteraction(Uri uri);
    }

}
