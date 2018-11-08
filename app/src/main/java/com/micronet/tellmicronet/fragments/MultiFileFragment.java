package com.micronet.tellmicronet.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.micronet.tellmicronet.R;
import com.micronet.tellmicronet.util.FileUtils;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultiFileFragment extends BaseInformationFragment {

    public static final String EXTRA_INFORMATION = "Extra information";
    private Spinner fileSelect;
    private TextView fileView;
    private ProgressBar loadingSpinner;
    List<String> fileList;
    String extraInfo;

    public void setInfo(List<String> files, String extraInfo) {
        this.fileList = files;
        this.extraInfo = extraInfo;
        if(!"".equals(extraInfo) && !(extraInfo == null)) {
            this.fileList.add("Extra information");
        }
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
                if(!EXTRA_INFORMATION.equals(fileList.get(i))) {
                    displayFile(fileList.get(i));
                }
                else {
                    fileView.setText(extraInfo);
                }
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
