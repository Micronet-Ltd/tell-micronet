package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.fragments.BaseInformationFragment;
import com.micronet.tellmicronet.fragments.TableInformationFragment;

import java.util.HashMap;

/**
 * Created by austin.oneil on 10/8/2018.
 */

public class LargeTableInformation extends LargeInformation {
    protected void setInformation(HashMap<String, String> information) {
        this.information = information;
    }

    HashMap<String, String> information;

    public LargeTableInformation(HashMap<String, String> information) {
        this.information = information;
    }

    @Override
    protected String extraInfo() {
        StringBuilder builder = new StringBuilder();
        for (String key : information.keySet()) {
            builder.append(key);
            builder.append(":\t");
            builder.append(information.get(key));
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public TableInformationFragment generateFragment() {
        TableInformationFragment fragment = new TableInformationFragment();
        fragment.setInformationHashMap(information);
        return fragment;
    }
}
