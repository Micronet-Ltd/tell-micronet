package com.micronet.tellmicronet.util;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by austin.oneil on 10/8/2018.
 */

public class FileUtils {
    public static String readFileContents(String filePath) throws IOException {
        List<String> results = eu.chainfire.libsuperuser.Shell.SU.run("cat " + filePath);
        Shell.SU.clearCachedResults();
        return multiLineString(results);
    }

    public static String multiLineString(List<String> list) {
        if(list == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }

    public static List<String> fileNameList(String directory) {
        List<String> fileList = Shell.SU.run("ls " + directory);
        return fileList;
    }

    public static List<File> fileList(String directory) {
        ArrayList<File> fileList = new ArrayList<>();
        File folder = new File(directory);

        File[] listOfFiles = folder.listFiles();
        if(listOfFiles == null) {
            return fileList;
        }
        for (File f : listOfFiles) {
            if(f.isFile()) {
                fileList.add(f);
            }
        }
        return fileList;
    }

    public static String loggingDirectory() {
        return "/sdcard/Device_" + android.os.Build.SERIAL + "_Debug";
    }

    public static String deviceStoragePath(String device) {
        if(Devices.A317.equals(device)) {
            return "/data/internal_Storage/";
        }
        else if (Devices.SMART_HUB.equals(device)){
            return "/storage/sdcard0/";
        }
        return null;
    }

    public static File ZipFiles(HashMap<String, String> filesToBeZipped, String destinationPath) throws IOException {
        final int BUFFER = 1024;
        BufferedInputStream origin = null;
        FileOutputStream dest = new FileOutputStream(destinationPath + "/tellmicronet.zip");
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
        byte[] data = new byte[BUFFER];
        for (String source : filesToBeZipped.keySet()) {
            FileInputStream fileInputStream = new FileInputStream(source);
            origin = new BufferedInputStream(fileInputStream, BUFFER);

            ZipEntry entry = new ZipEntry(filesToBeZipped.get(source));
            out.putNextEntry(entry);

            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            origin.close();
        }
        out.close();
        return new File(destinationPath + "/tellmicronet.zip");
    }

    public static void generateTextFile(String path, String text) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(path, true);
        outputStream.write(text.getBytes());
        outputStream.close();
    }

    public static String tableString(String tableName, String sqliteFilePath) {
        String sqlCommand = "SELECT * FROM " + tableName + ";" ;
        String fullCommand = shellSqlCommand(sqliteFilePath, sqlCommand);
//S
        return ShellExecutor.execute(fullCommand);
//        return multiLineString(result);
    }

    @NonNull
    public static String shellSqlCommand(String sqliteFilePath, String sqlCommand) {
        return "sqlite3 " + sqliteFilePath + " \'" + sqlCommand + "\'";
    }

    public static List<String> tableList(String sqliteFilePath) {

        String sqlCommand = "SELECT name FROM sqlite_master WHERE type=\"table\";";
        String fullCommand = shellSqlCommand(sqliteFilePath, sqlCommand);
        String[] tableNames = ShellExecutor.execute(fullCommand).split("\n");
        List<String> retList = new ArrayList<>();
        for (String name : tableNames) {
            retList.add(name);
        }
        return retList;
    }

    public static List<String> columns(String sqliteFilePath, String table) {
        List<String> columns = new ArrayList<>();
        String sqlCommand = "PRAGMA table_info(" + table + ");";
        String fullCommand = shellSqlCommand(sqliteFilePath, sqlCommand);
        List<String> rows = Shell.SU.run(fullCommand);
        for (String row : rows) {
            String[] entries = row.split("\\|");
            columns.add(entries[1]);
        }
        return columns;
    }
}
