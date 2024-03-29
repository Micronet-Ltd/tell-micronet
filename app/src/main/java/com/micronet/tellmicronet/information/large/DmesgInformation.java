package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin.oneil on 10/9/2018.
 */

public class DmesgInformation extends MultiFileInformation {

    @Override
    public ArrayList<String> filePaths() {
        List<File> files = FileUtils.fileList(FileUtils.loggingDirectory());
        ArrayList<String> paths = new ArrayList<>();
        for (File f : files) {
            if(f.getName().contains("dmesg")) {
                paths.add(f.getAbsolutePath());
            }
        }
        return paths;
    }
}