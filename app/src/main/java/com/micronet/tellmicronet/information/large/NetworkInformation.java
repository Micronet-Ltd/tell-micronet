package com.micronet.tellmicronet.information.large;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.micronet.tellmicronet.fragments.TableInformationFragment;

import java.util.HashMap;

/**
 * Created by austin.oneil on 10/12/2018.
 */

public class NetworkInformation extends LargeTableInformation {
    private Context context;
    private static final String NO = "NO";
    private static final String YES = "YES";
    private SignalStrength deviceSignalStrength;

    public NetworkInformation(Context context) {
        super(null);
        this.context = context;

    }

    private HashMap<String, String> infoMap(Context context){
        HashMap<String, String> hashMap = new HashMap<>();

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener listener = new MyPhoneStateListener();
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);


        hashMap.put("CDMA signal strength", deviceSignalStrength.getCdmaDbm() + " dBm");
        hashMap.put("EVDO signal strength", deviceSignalStrength.getEvdoDbm() + " dBm");
        hashMap.put("GSM signal strength", String.valueOf(deviceSignalStrength.getGsmSignalStrength()));
        hashMap.put("Device type", deviceSignalStrength.isGsm() ? "GSM" : "CDMA");

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        hashMap.put("Is network connected?", isConnected(activeNetwork) ? YES : NO);
        hashMap.put("Is network roaming?", activeNetwork.isRoaming() ? YES : NO);

        return hashMap;
    }

    private boolean isConnected(NetworkInfo activeNetwork) {
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            deviceSignalStrength = signalStrength;
            super.onSignalStrengthsChanged(signalStrength);
        }
    }

    @Override
    public TableInformationFragment generateFragment() {
        TableInformationFragment f = super.generateFragment();
        f.setInformationHashMap(infoMap(context));
        return f;
    }
}
