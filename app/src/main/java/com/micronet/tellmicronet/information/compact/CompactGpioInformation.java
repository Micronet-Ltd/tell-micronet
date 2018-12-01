package com.micronet.tellmicronet.information.compact;

import eu.chainfire.libsuperuser.Shell;
import micronet.hardware.MicronetHardware;

/**
 * Created by austin.oneil on 11/5/2018.
 */

public class CompactGpioInformation extends CompactInformation {
    private static final int TOTAL_GPIOS = 3; // TODO: make more flexible for other devices

    @Override
    public String retrieveInfo() {
        StringBuilder sb = new StringBuilder();
        for(int i=1; i <= TOTAL_GPIOS; i++) {
            exportGpio(i);
            sb.append("GPIO ").append(i).append(": ").append(getValue(i)).append("; ");
        }
        return sb.toString();
    }

    private void exportGpio(int gpioNumber) {
        Shell.SU.run("echo " + gpioNumber + " > /sys/class/gpio/export");
    }

    protected int getValue(int gpioNumber) {
        String path = "/sys/class/gpio/gpio"+gpioNumber+"/value";
        return Integer.parseInt(Shell.SU.run("cat " + path).get(0));
    }
}
