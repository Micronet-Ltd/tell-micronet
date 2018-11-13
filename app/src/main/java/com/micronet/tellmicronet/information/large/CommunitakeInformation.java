package com.micronet.tellmicronet.information.large;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by austin.oneil on 11/9/2018.
 */

public class CommunitakeInformation extends MultiFileInformation {
    @Override
    public List<String> filePaths() {
        List<String> paths = new ArrayList<>();
        if(new File(Environment.getExternalStorageDirectory() + "/ctlog.txt").exists()) {
            paths.add(Environment.getExternalStorageDirectory() + "/ctlog.txt");
        }
        if(new File(Environment.getExternalStorageDirectory() + "/ctmydevicelog.txt").exists()) {
            paths.add(Environment.getExternalStorageDirectory() + "/ctmydevicelog.txt");
        }
        if(new File(Environment.getExternalStorageDirectory() + "/ctrsservicelog.txt").exists()) {
            paths.add(Environment.getExternalStorageDirectory() + "/ctrsservicelog.txt");
        }
        File tempDirectory = Environment.getDataDirectory();
        String directoryPath = tempDirectory.getPath();
        if(new File("/data/data/com.communitake.android.gsdcare/app_log/ctlog.txt").exists()) {
            Shell.SU.run("cp /data/data/com.communitake.android.gsdcare/app_log/ctlog.txt " + directoryPath + "/gsdcare_ctlog.txt");
            paths.add(directoryPath + "/gsdcare_ctlog.txt");
            Shell.SU.run("chmod 777 " + directoryPath + "/gsdcare_ctlog.txt");
        }
        if(new File("./data/data/com.communitake.remotecontrolservice/app_log/ctlog.txt").exists()) {
            Shell.SU.run("cp /data/data/com.communitake.remotecontrolservice/app_log/ctlog.txt " + directoryPath + "/remotecontrol_ctlog.txt");
            paths.add(directoryPath + "/remotecontrol_ctlog.txt");
            Shell.SU.run("chmod 777 " + directoryPath + "/remotecontrol_ctlog.txt");
        }

        return paths;
    }
}
