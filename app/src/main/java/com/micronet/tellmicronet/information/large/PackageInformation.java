package com.micronet.tellmicronet.information.large;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.HashMap;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by austin.oneil on 11/9/2018.
 */

public class PackageInformation extends LargeTableInformation {

    Context context;

    public PackageInformation(Context context) {
        super();
        this.context = context;
        setInformation(informationSet());
    }

    private HashMap<String, String> informationSet() {
        HashMap<String, String> packages = new HashMap<>();
        List<String> packageList = Shell.SU.run("pm list packages");
        for (String rawPackageName : packageList) {
            String packageName = rawPackageName.substring(8);
            try {
                packages.put(packageName, context.getPackageManager().getPackageInfo(packageName, 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                // probably won't happen, but we need to log exceptions
                e.printStackTrace();
            }
        }
        return packages;
    }
}
