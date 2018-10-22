package com.micronet.tellmicronet.util;

/**
 * Created by austin.oneil on 10/2/2018.
 */

// TO ANYBODY WHO WANTS TO MODIFY THIS FILE:
// This file will contain a string for every product we are building this app for.
// In order to get the string for a new device, run `adb shell getprop ro.build.product`.

public final class Devices {
    public static final String A317 = "a317";

    public static String thisDevice() {
        return System.getProperty("ro.build.process");
    }
}
