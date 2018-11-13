package com.micronet.tellmicronet.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;

import javax.xml.datatype.Duration;

public class ZipReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(final Context context, Intent intent) {
        final String targetPath = intent.getStringExtra("path");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(targetPath == null) {
                    try {
                        InformationGatherer.generateZipFromInformation(InformationGatherer.largeInformationList(context), Devices.thisDevice());

                        Toast toast = new Toast(context);
                        toast.makeText(context, "Zipping file done", Toast.LENGTH_LONG);
                        toast.show();
                    } catch (IOException e) {
                        Looper.prepare();
//                        Toast toast = new Toast(context);
//                        toast.makeText(context, "There was an IOException generating the zip file.", Toast.LENGTH_LONG);
//                        toast.show();
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
}
