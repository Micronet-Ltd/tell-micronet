package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.util.FileUtils;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by austin.oneil on 11/9/2018.
 */

public class ApnInformation extends DatabaseInformation {
    public ApnInformation() {
        super("/data/data/com.android.providers.telephony/databases/telephony.db");
    }

    @Override
    public String extraInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Preferred APN \n");
        String info = FileUtils.multiLineString(Shell.SU.run("cat /data/data/com.android.providers.telephony/shared_prefs/preferred-apn.xml"));
        sb.append(info).append("\n");
        info = FileUtils.multiLineString(Shell.SU.run("adb shell cat /data/data/com.android.settings/shared_prefs/com.android.settings_preferences.xml"));
        sb.append(info).append("\n\n\n");
        sb.append("==============================================\n");
        sb.append(super.extraInfo());
        return sb.toString();
    }

    @Override
    public List<String> filePaths() {

        return super.filePaths();
    }
}
