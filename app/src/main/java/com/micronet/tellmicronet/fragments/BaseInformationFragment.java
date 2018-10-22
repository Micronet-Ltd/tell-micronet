package com.micronet.tellmicronet.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.micronet.tellmicronet.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseInformationFragment extends Fragment {
    private String text;
    TextView informationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_information, container, false);
        informationView = view.findViewById(R.id.information);
        informationView.setTextSize(18.0f);
        informationView.setText(text);
        return view;
    }

    public void setText(String text) {
        this.text = text;
    }
}
