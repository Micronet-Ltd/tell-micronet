package com.micronet.tellmicronet;

import com.micronet.tellmicronet.information.DeviceInformation;
import com.micronet.tellmicronet.information.compact.CompactInformation;
import com.micronet.tellmicronet.information.large.LargeInformation;

import java.util.HashMap;

/**
 * Created by austin.oneil on 9/28/2018.
 */

public class InformationType {
    private String name;
    private HashMap<String, DeviceInformation> infoMap;
    private DeviceInformation defaultDeviceInformation;

    public InformationType(String name, DeviceInformation defaultDeviceInformation) {
        this.name = name;
        this.defaultDeviceInformation = defaultDeviceInformation;
        this.infoMap = new HashMap<>();
    }

    public void addCommand(String deviceType, DeviceInformation command) {
        this.infoMap.put(deviceType, command);
    }

    public DeviceInformation getCommand(String deviceType) {
        if(!infoMap.containsKey(deviceType)) {
            return this.defaultDeviceInformation;
        }
        return this.infoMap.get(deviceType);
    }

    public boolean isCompact(String deviceType) {
        return getCommand(deviceType) instanceof CompactInformation;
    }

    public boolean isLarge(String deviceType) {
        return getCommand(deviceType) instanceof LargeInformation;
    }

    public String getInformationName() {
        return this.name;
    }
}
