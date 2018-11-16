package com.micronet.tellmicronet;

import android.os.AsyncTask;

import java.io.File;

/**
 * Created by austin.oneil on 11/14/2018.
 */

public class UploadFileTask extends AsyncTask {
    File file;

    public UploadFileTask(File file) {
        this.file = file;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
