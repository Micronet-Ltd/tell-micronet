package com.micronet.tellmicronet.fragments;


import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.micronet.tellmicronet.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatabaseFragment extends BaseInformationFragment {

    String databaseFilePath;
    Spinner tableSelectSpinner;
    TableLayout tableView;


    public DatabaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        tableSelectSpinner = view.findViewById(R.id.file_selector);
        tableView = view.findViewById(R.id.table_view);
        return view;
    }
}
