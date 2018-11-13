package com.micronet.tellmicronet.information.large;

import android.app.Application;

import java.util.List;

/**
 * Created by austin.oneil on 11/9/2018.
 */

public class ApnInformation extends DatabaseInformation {
    public ApnInformation() {
        super("/data/data/com.android.providers.telephony/databases/telephony.db");
    }

    @Override
    public List<String> filePaths() {

        return super.filePaths();
    }
}
