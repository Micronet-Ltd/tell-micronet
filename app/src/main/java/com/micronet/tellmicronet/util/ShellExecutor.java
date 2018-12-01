package com.micronet.tellmicronet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by austin.oneil on 6/28/2018.
 */

public class ShellExecutor {

    public ShellExecutor() {

    }

    public static String execute(String command) {

//        StringBuilder output = new StringBuilder();
//
//        Process p;
//        try {
//            Runtime r = Runtime.getRuntime();
//
//            p = r.exec(command);
//            p.waitFor();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())); // return to getInputStream
//
//            String line = "";
//            while ((line=reader.readLine()) != null) {
//                output.append(line + "\n");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String response = output.toString();
        if(Devices.SMART_HUB.equals(Devices.thisDevice())) {
            try {
//                Runtime.getRuntime().exec("su");
                Process process = Runtime.getRuntime().exec(new String[]{/*"su", "-c", */command}, null, null);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                StringBuilder log = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    log.append(line).append("\n");
                }
                return log.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        else {
            return FileUtils.multiLineString(Shell.SU.run(command));
        }
    }
}