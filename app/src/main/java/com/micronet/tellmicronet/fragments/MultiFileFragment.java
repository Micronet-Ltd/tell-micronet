package com.micronet.tellmicronet.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.micronet.tellmicronet.R;
import com.micronet.tellmicronet.util.FileUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultiFileFragment extends BaseInformationFragment {

    private Spinner fileSelect;
    private TextView fileView;
    private ProgressBar loadingSpinner;
    String[] fileList;

    public void setFiles(ArrayList<String> files) {
        this.fileList = files.toArray(new String[0]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dmesg, container, false);
        fileSelect = (Spinner)view.findViewById(R.id.file_selector);
        fileView = (TextView) view.findViewById(R.id.file_view);
        fileView.setTextSize(18.0f);
        loadingSpinner = (ProgressBar)view.findViewById(R.id.loading_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, fileList);
        fileSelect.setAdapter(spinnerArrayAdapter);
        fileSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                displayFile(fileList[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });
        return view;
    }

    private void displayFile(final String filePath) {
        AsyncTask task = new AsyncTask() {
            String fileContents;

            @Override
            protected void onPreExecute() {
                loadingSpinner.setVisibility(View.VISIBLE);
                loadingSpinner.animate();
                fileView.setVisibility(View.GONE);
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Object[] objects) {
                String file = filePath;
                try {
                    fileContents = FileUtils.readFileContents(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            protected void onPostExecute(Object o) {
                fileView.setText(fileContents);
                loadingSpinner.setVisibility(View.GONE);
                fileView.setVisibility(View.VISIBLE);
                super.onPostExecute(o);
            }
        };
        task.execute();
    }
}
