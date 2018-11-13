package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.util.FileUtils;
import com.micronet.tellmicronet.util.ShellExecutor;

import eu.chainfire.libsuperuser.Shell;

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
//        ShellExecutor executor = new ShellExecutor();
//        String info = executor.execute(command);
        String info = FileUtils.multiLineString(Shell.SU.run(command));
        return info;
    }
}
