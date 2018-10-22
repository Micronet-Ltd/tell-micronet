package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.util.FileUtils;
import com.micronet.tellmicronet.util.ShellExecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin.oneil on 10/8/2018.
 */

public class LogcatInformation extends MultiFileInformation {
    @Override
    public ArrayList<String> filePaths() {
        List<File> files = FileUtils.fileList(FileUtils.loggingDirectory());
        ArrayList<String> paths = new ArrayList<>();
        for (File f : files) {
            if(f.getName().contains("logcat")) {
                paths.add(f.getAbsolutePath());
            }
        }
        return paths;
    }
}
