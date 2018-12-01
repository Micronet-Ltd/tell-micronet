package com.micronet.tellmicronet.information.large;

import android.util.Log;

import com.micronet.tellmicronet.util.ShellExecutor;

import java.util.HashMap;

/**
 * Created by austin.oneil on 10/16/2018.
 */

public class LargeGetpropInformation extends LargeTableInformation {

    private static HashMap<String, String> getpropHashCache = null;

    public LargeGetpropInformation() {
        super(getpropHash());
    }

    public static HashMap<String, String> getpropHash() {
        if(getpropHashCache == null) {
            getpropHashCache = new HashMap<>();
            String output = getpropString();
            String[] lines = output.split("\n");
            for (String line : lines) {
                try {
                    String[] splitLine = line.split(":");
                    String key = splitLine[0].trim().substring(1, splitLine[0].length() - 1);
                    String value = splitLine[1].trim().substring(1, splitLine[1].length() - 2);
                    getpropHashCache.put(key, value);
                }
                catch (StringIndexOutOfBoundsException e) {
                    Log.d("Tell-Micronet", "Bad string: " + line);
                }
            }
        }
        return getpropHashCache;
    }

    @Override
    public String extraInfoFileName() {
        return "properties.csv";
    }

    @Override
    public String retrieveInfo() {
        HashMap<String, String> map = getpropHash();
        StringBuilder retString = new StringBuilder("Property name\tProperty value\n");
        for (String key : map.keySet()) {
            retString.append(key);
            retString.append("\t");
            retString.append(map.get(key));
            retString.append("\n");
        }
        return retString.toString();
    }

    private static String getpropString() {
        return ShellExecutor.execute("getprop");
    }
}