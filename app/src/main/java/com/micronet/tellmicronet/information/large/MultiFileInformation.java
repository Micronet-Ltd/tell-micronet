package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.fragments.BaseInformationFragment;
import com.micronet.tellmicronet.fragments.MultiFileFragment;

/**
 * Created by austin.oneil on 10/11/2018.
 */

public class MultiFileInformation extends LargeInformation {
    @Override
    public BaseInformationFragment generateFragment() {
        MultiFileFragment fragment = new MultiFileFragment();
        fragment.setInfo(filePaths(), extraInfo());
        return fragment;
    }
}
