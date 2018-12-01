package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.util.FileUtils;
import com.micronet.tellmicronet.util.ShellExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by austin.oneil on 11/19/2018.
 */

public class LogcatInformation extends LargeInformation {
    @Override
    protected String extraInfo() {
        return FileUtils.multiLineString(Shell.SH.run("logcat -d"));
    }
}
