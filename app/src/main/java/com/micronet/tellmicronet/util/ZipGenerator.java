package com.micronet.tellmicronet.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by austin.oneil on 10/2/2018.
 */

public class ZipGenerator {
    public static void generateZipFile(ArrayList<String> files, String targetPath) {
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetPath));
            for (String path : files) {
                ZipEntry zipEntry = new ZipEntry(path);
                out.putNextEntry(zipEntry);
                out.closeEntry();
            }
            out.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
