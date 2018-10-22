package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.fragments.BaseInformationFragment;
import com.micronet.tellmicronet.information.DeviceInformation;

import java.util.ArrayList;

/**
 * Created by austin.oneil on 9/27/2018.
 */

public class LargeInformation extends DeviceInformation {

    public LargeInformation() {

    }

    public ArrayList<String> filePaths() {
        return new ArrayList<>();
    }

    protected String extraInfo() {
        return "";
    }

    @Override
    public String retrieveInfo() {
        return extraInfo();
    }

    public BaseInformationFragment generateFragment() {
        BaseInformationFragment fragment = new BaseInformationFragment();
        fragment.setText(extraInfo());
        return fragment;
    }
}
