package com.micronet.tellmicronet.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.micronet.tellmicronet.R;
import com.micronet.tellmicronet.util.Database;

import java.util.ArrayList;
import java.util.HashMap;

import static com.micronet.tellmicronet.R.color.colorPrimary;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatabaseFragment extends BaseInformationFragment {

    Database database;
    Spinner tableSelectSpinner;
    TableLayout tableView;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public DatabaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        tableSelectSpinner = view.findViewById(R.id.table_select);
        tableView = view.findViewById(R.id.table_view);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item,  new ArrayList<>(database.tableNames()));
        tableSelectSpinner.setAdapter(spinnerArrayAdapter);
        tableSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String key = (String)((TextView)view).getText();

                if(key != null && !"".equals(key)) {
                    Database.Table table = database.getTable(key);
                    generateTable(table);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
        return view;
    }



    private void generateTable(Database.Table table) {
        Context context = getContext();
        tableView.removeAllViews();
        TableRow row = new TableRow(context);
        ArrayList<String> columns = new ArrayList<String>(table.getColumns());
        for (String column : columns) {
            TextView tv = new TextView(context);
            tv.setText(column);
            tv.setTextColor(getResources().getColor(colorPrimary));
            row.addView(tv);
        }
        tableView.addView(row);
//        for (HashMap<String, String> rowData : table.getRows()) {
         for(int i=0; i<100; i++) {
            if(i < table.getRows().size()) {
                HashMap<String, String> rowData = table.getRows().get(i);
                TableRow rowView = new TableRow(context);
                for (String column : columns) {
                    TextView textView = new TextView(context);
                    textView.setText(rowData.get(column));
                    rowView.addView(textView);
                }
                tableView.addView(rowView);
            }
            else {
                i = 100;
            }
        }
    }
}
