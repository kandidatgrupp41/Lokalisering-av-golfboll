package com.devace.johannesberghult.blutoothapi21test.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.devace.johannesberghult.blutoothapi21test.Misc.Device;
import com.devace.johannesberghult.blutoothapi21test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindBallFragment.FindBallInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindBallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindBallFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FindBallInteractionListener mListener;

    private Device trackDevice;
    private ImageView findBackground;
    private ImageView golfballImage;
    private ImageView rangeTextImage;
    private TextView deviceNametxt;


    private Button backButton;

    private BluetoothManager manager;
    private BluetoothAdapter bluetoothAdapter;

    private BluetoothLeScanner bluetoothLeScanner;

    private Animation mAnimation;
    private float currentYPos = 0f;
    private float toPos;

    private boolean init = true;

    private List<Integer> lastRssis = new ArrayList<Integer>();
    private double meanRssi;
    private int sumRssi;

    private Vibrator vibrator;

    public FindBallFragment() {
        // Required empty public constructor
    }

    public void setDevice(Device device){
        trackDevice=device;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindBallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindBallFragment newInstance(String param1, String param2) {
        FindBallFragment fragment = new FindBallFragment();
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

        View view = inflater.inflate(R.layout.fragment_find_ball, container, false);

        findBackground = (ImageView) view.findViewById(R.id.find__background);
        findBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        golfballImage = (ImageView) view.findViewById(R.id.golfball_view);
        golfballImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        rangeTextImage = (ImageView) view.findViewById(R.id.rangetext_view);
        rangeTextImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        deviceNametxt = (TextView) view.findViewById(R.id.name_text);

        deviceNametxt.setText(trackDevice.getUserName());
        golfballImage.setAlpha(0f);

        backButton = (Button)view.findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        backButton.setAlpha(0f);

        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFindBallInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FindBallInteractionListener) {
            mListener = (FindBallInteractionListener) context;
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

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            if (result.getDevice().getAddress().equals(trackDevice.getAddress())){
                if (lastRssis.size()==10) {
                    lastRssis.remove(0);
                    lastRssis.add(result.getRssi());
                }else{
                    lastRssis.add(result.getRssi());
                }
                for (int tempRssi: lastRssis){
                    sumRssi += tempRssi;
                }
                meanRssi = sumRssi / lastRssis.size();
                sumRssi=0;
            }


            if(init&&result.getDevice().getAddress().equals(trackDevice.getAddress())){
                golfballImage.setAlpha(1f);
                vibrator.vibrate(500);

                if(meanRssi>=-60){
                    toPos = 0.88f;
                }else if(meanRssi<=-90){
                    toPos = 0f;
                }
                else{
                    //y = 0.0293333 x + 2.64
                    double toPosDouble = 2.64 + 0.0293333 * meanRssi;
                    toPos = (float) toPosDouble;

                    toPos=0.88f;
                }
                init = false;
                mAnimation=newAnimation(0f,0f,currentYPos,toPos);
                mAnimation.setAnimationListener(animationListener);
                golfballImage.startAnimation(mAnimation);
            } else if(result.getDevice().getAddress().equals(trackDevice.getAddress())&& mAnimation.hasEnded()){
                golfballImage.setAlpha(1f);
                // rssi between -40 and -100
                // Y-anim between 0 and 0.88

                if(meanRssi>=-60){
                    toPos = 0.88f;
                }else if(meanRssi<=-90){
                    toPos = 0f;
                }
                else{
                //y = 0.0293333 x + 2.64
                    double toPosDouble = 2.64 + 0.0293333 * meanRssi;
                    toPos = (float) toPosDouble;
                }
                mAnimation=newAnimation(0f,0f,currentYPos,toPos);
                mAnimation.setAnimationListener(animationListener);
                golfballImage.startAnimation(mAnimation);
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

    private Animation.AnimationListener animationListener=new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
        currentYPos = toPos;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationStart(Animation animation) {

        }
    };

    private Animation newAnimation(float startX, float endX, float startY, float endY){
        Animation mAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, startX,
                TranslateAnimation.RELATIVE_TO_PARENT, endX,
                TranslateAnimation.RELATIVE_TO_PARENT, startY,
                TranslateAnimation.RELATIVE_TO_PARENT, endY );
        mAnimation.setDuration(400);
        mAnimation.setRepeatCount(0);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setFillEnabled(true);
        mAnimation.setFillAfter(true);



        return mAnimation;
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
    public interface FindBallInteractionListener {
        // TODO: Update argument type and name
        void onFindBallInteraction(Uri uri);
    }
}

