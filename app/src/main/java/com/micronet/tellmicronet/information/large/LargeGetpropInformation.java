package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.util.ShellExecutor;

import java.util.HashMap;

/**
 * Created by austin.oneil on 10/16/2018.
 */

public class LargeGetpropInformation extends LargeTableInformation {

    public LargeGetpropInformation() {
        super(getpropHash());
    }

    public static HashMap<String, String> getpropHash() {
        HashMap<String, String> map = new HashMap<>();
        String output = getpropString();
        String[] lines = output.split("\n");
        for (String line : lines) {
            String[] splitLine = line.split(":");
            String key = splitLine[0].trim().substring(1, splitLine[0].length()-1);
            String value = splitLine[1].trim().substring(1, splitLine[1].length()-2);
            map.put(key, value);
        }
        return map;
    }

    @Override
    public String retrieveInfo() {
        return getpropString();
    }

    private static String getpropString() {
        ShellExecutor executor = new ShellExecutor();
        return executor.execute("getprop");
    }
}