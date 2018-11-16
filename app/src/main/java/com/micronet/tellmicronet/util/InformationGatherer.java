package com.micronet.tellmicronet.util;

import android.content.Context;

import com.dropbox.core.DbxException;
import com.micronet.tellmicronet.InformationType;
import com.micronet.tellmicronet.information.compact.CompactCommandInformation;
import com.micronet.tellmicronet.information.compact.CompactFileInformation;
import com.micronet.tellmicronet.information.compact.CompactGpioInformation;
import com.micronet.tellmicronet.information.compact.CompactInformation;
import com.micronet.tellmicronet.information.compact.CompactQbridgeInformation;
import com.micronet.tellmicronet.information.large.ApnInformation;
import com.micronet.tellmicronet.information.large.CommunitakeInformation;
import com.micronet.tellmicronet.information.large.DatabaseInformation;
import com.micronet.tellmicronet.information.large.DmesgInformation;
import com.micronet.tellmicronet.information.large.LargeCommandInformation;
import com.micronet.tellmicronet.information.large.LargeGetpropInformation;
import com.micronet.tellmicronet.information.large.LargeInformation;
import com.micronet.tellmicronet.information.large.LargeTableInformation;
import com.micronet.tellmicronet.information.large.LogcatInformation;
import com.micronet.tellmicronet.information.large.PackageInformation;
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
        list.add(new InformationType("Logcat", new LogcatInformation()));
        list.add(new InformationType("Package list", new PackageInformation(context)));
//        list.add(new InformationType("Package list", new LargeCommandInformation("pm list packages")));
        list.add(new InformationType("OS version", new CompactCommandInformation("getprop ro.build.description")));
        list.add(new InformationType("Uboot information", new CompactFileInformation("/sys/module/device/parameters/ubootver")));
        list.add(new InformationType("MCU version", new CompactFileInformation("/sys/module/device/parameters/mcuver")));
        list.add(new InformationType("Kernel information", new CompactFileInformation("/proc/version")));
        list.add(new InformationType("Dmesg information", new DmesgInformation()));
        list.add(new InformationType("APN database", new ApnInformation()));
        list.add(new InformationType("Currently running processes", new LargeCommandInformation("ps")));
        list.add(new InformationType("Bootloader", new CompactCommandInformation("getprop ro.bootloader")));
//        list.add(new InformationType("Network information", new NetworkInformation(context)));
        list.add(new InformationType("System properties", new LargeGetpropInformation()));
        list.add(new InformationType("Tombstones", new TombstoneInformation()));
        list.add(new InformationType("Redbend information", new RedbendInformation()));
        list.add(new InformationType("QBridge version information", new CompactQbridgeInformation(context)));
        list.add(new InformationType("GPIOs", new CompactGpioInformation()));
        list.add(new InformationType("Communitake logs", new CommunitakeInformation()));
        return list;
    }

    public static List<InformationType> largeInformationList(Context context) {
        List<InformationType> informationList = gatherInformation(context);
        List<InformationType> infoList = new ArrayList<>();

        for (InformationType info : informationList) {
            if(!info.isCompact(Devices.thisDevice())) {
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
        String tempDirectory = "/data/internal_Storage/tellmicronettemp";
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
        File zippedFile = FileUtils.ZipFiles(informationMap, "/data/internal_Storage/");
        DropboxHelper.getInstance().UploadFile(zippedFile);
        dir.delete();
    }

}
