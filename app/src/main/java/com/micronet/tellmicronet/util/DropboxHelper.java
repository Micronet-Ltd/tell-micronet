package com.micronet.tellmicronet.util;

import android.os.Build;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by austin.oneil on 11/14/2018.
 */

public class DropboxHelper {
    private static final String ACCESS_TOKEN = "LPPT11VZzEAAAAAAAAAAOdmt4sJ4wN-o9iXjRX-nvuiYV4kFDRCmaU5FOsP9Ylh0";

    private static DropboxHelper _instance;

    DbxRequestConfig config;
    DbxClientV2 client;

    private DropboxHelper() {
        config = DbxRequestConfig.newBuilder("Tell Micronet").build();
        client = new DbxClientV2(config, ACCESS_TOKEN);

    }

    public static DropboxHelper getInstance() {
        if(_instance == null) {
            _instance = new DropboxHelper();
        }
        return _instance;
    }

    public void UploadFile(File file) throws DbxException {
        try {
            InputStream inputStream = new FileInputStream(file);
            client.files().uploadBuilder(fileName())
                    .withMode(WriteMode.ADD)
                    .uploadAndFinish(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileName() {
        return  "/tellmicronet/" + Build.SERIAL + "/" + Build.SERIAL + "_" + System.currentTimeMillis() + ".zip";
    }
}
