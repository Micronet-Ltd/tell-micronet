package com.micronet.tellmicronet.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;

import com.dropbox.core.DbxException;
import com.micronet.tellmicronet.InformationType;
import com.micronet.tellmicronet.information.NullInformation;
import com.micronet.tellmicronet.information.compact.CompactCommandInformation;
import com.micronet.tellmicronet.information.compact.CompactFileInformation;
import com.micronet.tellmicronet.information.compact.CompactGpioInformation;
import com.micronet.tellmicronet.information.compact.CompactInformation;
import com.micronet.tellmicronet.information.compact.CompactQbridgeInformation;
import com.micronet.tellmicronet.information.compact.SmarthubGpioInformation;
import com.micronet.tellmicronet.information.large.ApnInformation;
import com.micronet.tellmicronet.information.large.CommunitakeInformation;
import com.micronet.tellmicronet.information.large.ContentProviderInformation;
import com.micronet.tellmicronet.information.large.DmesgInformation;
import com.micronet.tellmicronet.information.large.LargeCommandInformation;
import com.micronet.tellmicronet.information.large.LargeGetpropInformation;
import com.micronet.tellmicronet.information.large.LargeInformation;
import com.micronet.tellmicronet.information.large.LargeTableInformation;
import com.micronet.tellmicronet.information.large.A317LogcatInformation;
import com.micronet.tellmicronet.information.large.LogcatInformation;
import com.micronet.tellmicronet.information.large.RedbendInformation;
import com.micronet.tellmicronet.information.large.TombstoneInformation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by austin.oneil on 10/5/2018.
 */


// Naming things is hard.
public class InformationGatherer {
    private static List<InformationType> gatherInformation(Context context) {
        List<InformationType> list = new ArrayList<>();
        InformationType mnfrParameters = new InformationType("Manufacturing parameters", new CompactFileInformation("sys/module/device/parameters/mnfrparams"));
        list.add(mnfrParameters);
        InformationType logcat = new InformationType("Logcat", new LogcatInformation());
        logcat.addCommand(Devices.A317, new A317LogcatInformation());
        list.add(new InformationType("Logcat", new LogcatInformation()));
//        list.add(new InformationType("Package list", new PackageInformation(context))); // TODO: uncomment and fix for SmartHub
        list.add(new InformationType("OS version", new CompactCommandInformation("getprop ro.build.description")));
        list.add(new InformationType("Uboot information", new CompactFileInformation("/sys/module/device/parameters/ubootver")));
        list.add(new InformationType("MCU version", new CompactFileInformation("/sys/module/device/parameters/mcuver")));
        list.add(new InformationType("Kernel information", new CompactFileInformation("/proc/version")));
        list.add(new InformationType("Dmesg information", new DmesgInformation()));

        InformationType apnInformation = new InformationType("APN database", new NullInformation());
        apnInformation.addCommand(Devices.A317, new ApnInformation());
        apnInformation.addCommand(Devices.SMART_HUB, new ContentProviderInformation(apnHashmap(), context));
        list.add(apnInformation);

        list.add(new InformationType("Currently running processes", new LargeCommandInformation("ps")));
        list.add(new InformationType("Bootloader", new CompactCommandInformation("getprop ro.bootloader")));
        list.add(new InformationType("System properties", new LargeGetpropInformation()));
        list.add(new InformationType("Tombstones", new TombstoneInformation()));

        InformationType redbendInformation = new InformationType("RedbendInformation", new RedbendInformation());
        redbendInformation.addCommand(Devices.SMART_HUB, new NullInformation());
        list.add(new InformationType("Redbend information", new RedbendInformation())); // TODO: uncomment and fix for SmartHub

        InformationType qbridgeInformationType = new InformationType("QBridge version information", new CompactQbridgeInformation(context));
        qbridgeInformationType.addCommand(Devices.SMART_HUB, new NullInformation());
        list.add(qbridgeInformationType);

        InformationType gpioInformation = new InformationType("GPIOs", new CompactGpioInformation());
        gpioInformation.addCommand(Devices.SMART_HUB, new SmarthubGpioInformation());
        list.add(gpioInformation);
        InformationType communitakeLogs = new InformationType("Communitake logs", new NullInformation());
        communitakeLogs.addCommand(Devices.A317, new CommunitakeInformation());
        list.add(communitakeLogs);
        return list;
    }

    private static HashMap<String, Uri> apnHashmap() {
        HashMap<String, Uri> map = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            map.put("carriers", Telephony.Carriers.CONTENT_URI);
        }
        return map;
    }

    public static List<InformationType> largeInformationList(Context context) {
        List<InformationType> informationList = gatherInformation(context);
        List<InformationType> infoList = new ArrayList<>();

        for (InformationType info : informationList) {
            if(info.isLarge(Devices.thisDevice())) {
                infoList.add(info);
            }
        }
        infoList.add(new InformationType("Miscellaneous device information", compactInformation(informationList)));
        return infoList;
    }

    public static LargeTableInformation compactInformation(List<InformationType> information) {
        HashMap<String, String> informationHashMap = new HashMap<>();
        for (InformationType info : information) {
            if(info.isCompact(Devices.thisDevice())) {
                informationHashMap.put(info.getInformationName(), ((CompactInformation) info.getCommand(Devices.thisDevice())).getInformation());
            }
        }
        return new LargeTableInformation(informationHashMap);
    }

    public static void generateZipFromInformation(List<InformationType> informationList, String device) throws IOException, DbxException {
        String tempDirectory = FileUtils.deviceStoragePath(device) + "tellmicronettemp";
        generateZipFromInformation(informationList, device, tempDirectory);
    }

    public static void generateZipFromInformation(List<InformationType> informationList, String device, String tempDirectory) throws IOException, DbxException {
        HashMap<String, String> informationMap = new HashMap<>();
        File dir = new File(tempDirectory);
        dir.mkdir();
        for (InformationType information : informationList) {
            String tempFilePath = tempDirectory + "/" + information.getInformationName();
            LargeInformation largeInformation = (LargeInformation)information.getCommand(device);
            FileUtils.generateTextFile(tempFilePath, largeInformation.retrieveInfo());
            informationMap.put(tempFilePath, information.getInformationName() + "/" + largeInformation.extraInfoFileName());
            List<String> paths = largeInformation.filePaths();
            if(!paths.isEmpty()) {
                for (String file : paths) {
                    String path = information.getInformationName() + "/" + file.substring(file.lastIndexOf("/") + 1);
                    informationMap.put(file, path);
                }
            }
        }
        File zippedFile = FileUtils.ZipFiles(informationMap, FileUtils.deviceStoragePath(device));
        DropboxHelper.getInstance().UploadFile(zippedFile);
        dir.delete();
    }

}

