package com.micronet.tellmicronet.information.compact;

import micronet.hardware.MicronetHardware;

/**
 * Created by austin.oneil on 11/30/2018.
 */

public class SmarthubGpioInformation extends CompactInformation {

    @Override
    public String retrieveInfo() {
        MicronetHardware hardware = MicronetHardware.getInstance();
        StringBuilder sb = new StringBuilder();
        int[] inputs = hardware.getAllPinInState();
        for (int i=0; i<inputs.length; i++) {
            sb.append("input ").append(i + 1).append(": ").append(inputs[i]).append("; ");
        }
        return sb.toString();
    }
}
