package com.micronet.tellmicronet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by austin.oneil on 10/2/2018.
 */

// TO ANYBODY WHO WANTS TO MODIFY THIS FILE:
// This file will contain a string for every product we are building this app for.
// In order to get the string for a new device, run `adb shell getprop ro.build.product`.

public final class Devices {
    public static final String A317 = "a317";
    public static final String SMART_HUB = "msm8916_64";

    private static String thisDevice = null;

    public static String thisDevice() {
        if(thisDevice == null) {
            try {
                Process process = Runtime.getRuntime().exec("getprop ro.build.product");
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                StringBuilder log = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    log.append(line).append("\n");
                }
                thisDevice = log.toString().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return thisDevice;
    }
}
