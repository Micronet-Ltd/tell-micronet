package com.micronet.tellmicronet.information.large;

import android.content.Context;

import com.micronet.tellmicronet.util.FileUtils;
import com.micronet.tellmicronet.util.ShellExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin.oneil on 10/16/2018.
 */

public class RedbendInformation extends MultiFileInformation {
    @Override
    public ArrayList<String> filePaths() {
        ShellExecutor executor = new ShellExecutor();
        executor.execute("su");

        ArrayList<String> files = new ArrayList<>();
        List<File> list = FileUtils.fileList("/data/data/com.redbend.client/files");
        for (File file: list) {
            if(file.getName().matches("vdm.*\\.log")) {
                files.add(file.getAbsolutePath());
            }
        }
        return files;
    }
}
