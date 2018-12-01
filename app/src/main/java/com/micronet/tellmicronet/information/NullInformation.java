package com.micronet.tellmicronet.information;

/**
 * Created by austin.oneil on 11/19/2018.
 */

// This class is used when a device does not have this feature. For example, the SmartHub doesn't have Uboot or MCU versions.
public class NullInformation extends DeviceInformation {
    @Override
    public String retrieveInfo() {
        return null;
    }


}
