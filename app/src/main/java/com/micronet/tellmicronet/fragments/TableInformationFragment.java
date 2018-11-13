package com.micronet.tellmicronet.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.micronet.tellmicronet.R;

import java.util.Arrays;
import java.util.HashMap;

public class TableInformationFragment extends BaseInformationFragment {
    TableLayout informationTable;

    public void setInformationHashMap(HashMap<String, String> informationHashMap) {
        this.informationHashMap = informationHashMap;
    }

    private HashMap<String, String> informationHashMap;

    protected void createTable(HashMap<String, String> mapping) {
        int i=0;
        Object[] keys = mapping.keySet().toArray();
        Arrays.sort(keys);
        for (Object key : keys) {
            TableRow row = new TableRow(getContext());
            i++;
            if(i%2==0) {
                Color color = getResources().getColor()
                row.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            else {
                row.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            row.setPadding(1, 1, 1, 5);


            TextView keyView = new TextView(getContext());
            keyView.setText((String)key);
            keyView.setTypeface(keyView.getTypeface(), Typeface.BOLD);
            keyView.setTextColor(Color.WHITE);
            keyView.setTextSize(18.0f);
            row.addView(keyView);

            TextView valueView = new TextView(getContext());
            valueView.setText(mapping.get(key));
            valueView.setTextColor(Color.WHITE);
            valueView.setTextSize(18.0f);
            row.addView(valueView);

            informationTable.addView(row);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table_information, container, false);
        informationTable = (TableLayout) view.findViewById(R.id.information);
        createTable(informationHashMap);
        return view;
    }
}
