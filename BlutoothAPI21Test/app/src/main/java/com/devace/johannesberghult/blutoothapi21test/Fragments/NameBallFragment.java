package com.devace.johannesberghult.blutoothapi21test.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.devace.johannesberghult.blutoothapi21test.Activities.MainActivity;
import com.devace.johannesberghult.blutoothapi21test.Misc.Device;
import com.devace.johannesberghult.blutoothapi21test.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NameBallFragment.NameBallInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NameBallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NameBallFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private NameBallInteractionListener mListener;

    private ImageView nameBackground;
    private Button okButton, backButton;
    private EditText nameField;


    private List<Device> foundDevices;

    public NameBallFragment() {
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
     * @return A new instance of fragment NameBallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NameBallFragment newInstance(String param1, String param2) {
        NameBallFragment fragment = new NameBallFragment();
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
        View view = inflater.inflate(R.layout.fragment_name_ball, container, false);

        nameBackground = (ImageView) view.findViewById(R.id.name_background);
        nameBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        okButton = (Button) view.findViewById(R.id.ok_button);
        nameField = (EditText) view.findViewById(R.id.name_field);
        backButton = (Button) view.findViewById(R.id.name_back_button);

        okButton.setAlpha(0f);

        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Device currentDevice = foundDevices.get(foundDevices.size()-1);
                currentDevice.setUserName(nameField.getText().toString());
                foundDevices.set(foundDevices.size()-1, currentDevice);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadMainFragment(foundDevices);
            }
            });
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                foundDevices.remove(foundDevices.size()-1);
                getActivity().onBackPressed();
            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onNameBallFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NameBallInteractionListener) {
            mListener = (NameBallInteractionListener) context;
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
    public interface NameBallInteractionListener {
        // TODO: Update argument type and name
        void onNameBallFragmentInteraction(Uri uri);
    }
}
