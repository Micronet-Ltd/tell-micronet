package com.micronet.tellmicronet.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.dropbox.core.DbxException;

import java.io.IOException;

import javax.xml.datatype.Duration;

public class ZipReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        final String targetPath = intent.getStringExtra("path");

        if(targetPath == null) {
            try {
                InformationGatherer.generateZipFromInformation(InformationGatherer.largeInformationList(context), Devices.thisDevice());
                Intent uploadCompleteIntent = new Intent("com.micronet.tellmicronet.intent.UPLOAD_COMPLETE");
                context.sendBroadcast(uploadCompleteIntent);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DbxException e) {
                Intent uploadFailedIntent = new Intent("com.micronet.tellmicronet.intent.UPLOAD_FAILED");
                context.sendBroadcast(uploadFailedIntent);
            }
        }
    }
}
