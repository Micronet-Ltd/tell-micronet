package com.micronet.tellmicronet.information.compact;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by austin.oneil on 11/2/2018.
 */

public class CompactQbridgeInformation extends CompactInformation {
    private Context context;

    public CompactQbridgeInformation(Context context) {
        this.context = context;
    }

    @Override
    public String retrieveInfo() {
        IntentFilter filter = new IntentFilter("com.micronet.qbupgrade.SEND_QB_VERSION_NUMBER");
        Intent intent = context.getApplicationContext().registerReceiver(null, filter);
        if(intent != null) {
            return intent.getStringExtra("QB_VERSION_NUMBER");
        }
        else {
            Intent retrieveQbVersionNumberIntent = new Intent("com.micronet.qbupgrade.GET_QB_VERSION_NUMBER");
            context.sendBroadcast(retrieveQbVersionNumberIntent);
            return null;
        }

    }
}
