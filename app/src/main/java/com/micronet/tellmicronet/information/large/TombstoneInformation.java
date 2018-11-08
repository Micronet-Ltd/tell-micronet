package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin.oneil on 10/25/2018.
 */

public class TombstoneInformation extends MultiFileInformation {
    @Override
    public List<String> filePaths() {
        List<File> files = FileUtils.fileList("/data/tombstones/");
        List<String> retList = new ArrayList<>();
        for (File f : files) {
            retList.add(f.getAbsolutePath());
        }
        return retList;
    }
}
