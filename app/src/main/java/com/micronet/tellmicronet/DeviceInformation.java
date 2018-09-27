package com.micronet.tellmicronet;

/**
 * Created by austin.oneil on 9/26/2018.
 */

public abstract class DeviceInformation {
    public abstract String retrieveInfo();
    public void createFile() {
        // Do nothing.  This is a hook.
    }
}
