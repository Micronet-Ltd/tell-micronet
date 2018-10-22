package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.util.ShellExecutor;

/**
 * Created by austin.oneil on 9/28/2018.
 */

public class LargeCommandInformation extends LargeInformation {
    private String command;

    public LargeCommandInformation(String command) {
        this.command = command;
    }

    @Override
    protected String extraInfo() {
        ShellExecutor executor = new ShellExecutor();
        String info = executor.execute(command);
        return info;
    }
}
