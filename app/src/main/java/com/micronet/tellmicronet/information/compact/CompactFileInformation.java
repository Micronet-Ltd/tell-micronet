package com.micronet.tellmicronet.information.compact;

import com.micronet.tellmicronet.util.FileUtils;

import java.io.IOException;

/**
 * Created by austin.oneil on 9/26/2018.
 */

public class CompactFileInformation extends CompactInformation {
    String filePath;

    public CompactFileInformation(String path) {
        filePath = path;
    }

    @Override
    public String retrieveInfo() {
        try {
            return FileUtils.readFileContents(filePath).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
