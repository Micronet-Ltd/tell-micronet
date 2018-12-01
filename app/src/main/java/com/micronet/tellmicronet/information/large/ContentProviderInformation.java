package com.micronet.tellmicronet.information.large;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.micronet.tellmicronet.fragments.BaseInformationFragment;
import com.micronet.tellmicronet.util.Database;

import java.util.HashMap;

/**
 * Created by austin.oneil on 11/29/2018.
 */

public class ContentProviderInformation extends DatabaseInformation {

    public ContentProviderInformation(HashMap<String, Uri> provider, Context context) {
        HashMap<String, Cursor> cursorMap = new HashMap<>();
        for (String tableName : provider.keySet()) {
            cursorMap.put(tableName, context.getContentResolver().query(provider.get(tableName), null, null, null, null));
        }
        database = new Database(cursorMap);
    }

    @Override
    public BaseInformationFragment generateFragment() {
        return super.generateFragment();
    }
}
