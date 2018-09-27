package com.micronet.tellmicronet;

import java.util.ArrayList;

/**
 * Created by austin.oneil on 9/27/2018.
 */

public class LargeInformation extends DeviceInformation {
    private String extraInfoFileName;

    public LargeInformation(String extraInfoFileName) {
        this.extraInfoFileName = extraInfoFileName;
    }

    public LargeInformation() {

    }

    protected ArrayList<String> filePaths() {
        return new ArrayList<>();
    }

    protected String extraInfo() {
        return "";
    }

    @Override
    public String retrieveInfo() {
        return extraInfo();
    }
}
