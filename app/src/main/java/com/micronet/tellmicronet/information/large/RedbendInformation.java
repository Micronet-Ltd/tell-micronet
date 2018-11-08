package com.micronet.tellmicronet.information.large;

import android.content.Context;
import android.os.Environment;

import com.micronet.tellmicronet.util.FileUtils;
import com.micronet.tellmicronet.util.ShellExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin.oneil on 10/16/2018.
 */

public class RedbendInformation extends MultiFileInformation {
    @Override
    public ArrayList<String> filePaths() {
        final String DIRECTORY = "/data/data/com.redbend.client/files";

        ArrayList<String> files = new ArrayList<>();
        List<String> list = FileUtils.fileNameList(DIRECTORY);
        for (String file: list) {
            if(file.matches("vdm.*\\.log")) {
                files.add(DIRECTORY + "/" + file);
            }
        }
        return files;
    }
}
