package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.fragments.BaseInformationFragment;
import com.micronet.tellmicronet.information.DeviceInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin.oneil on 9/27/2018.
 */

public class LargeInformation extends DeviceInformation {

    public LargeInformation() {

    }

    public List<String> filePaths() {
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

    // Override as needed.
    public String extraInfoFileName() {
        return "extra_info.txt";
    }
}
