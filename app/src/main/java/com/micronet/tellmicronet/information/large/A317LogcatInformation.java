package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by austin.oneil on 10/8/2018.
 */

public class A317LogcatInformation extends MultiFileInformation {
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

    @Override
    protected String extraInfo() {
        String logcat = FileUtils.multiLineString(Shell.SU.run("logcat -d"));
        return logcat;
    }

    @Override
    public String extraInfoFileName() {
        return "logcat_buffer.txt";
    }
}
