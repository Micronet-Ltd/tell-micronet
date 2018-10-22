package com.micronet.tellmicronet.information.compact;

import com.micronet.tellmicronet.util.ShellExecutor;

/**
 * Created by austin.oneil on 10/9/2018.
 */

public class CompactCommandInformation extends CompactInformation {
    String command;

    public CompactCommandInformation(String command) {
        this.command = command;
    }

    @Override
    public String retrieveInfo() {
        ShellExecutor executor = new ShellExecutor();
        return executor.execute(command);
    }
}
