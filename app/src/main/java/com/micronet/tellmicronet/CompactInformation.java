package com.micronet.tellmicronet;

/**
 * Created by austin.oneil on 9/26/2018.
 */

public abstract class CompactInformation extends DeviceInformation {
    private String information;
    public String getInformation() {
        if(information == null) {
            information = retrieveInfo();
        }
        return information;
    }
}
