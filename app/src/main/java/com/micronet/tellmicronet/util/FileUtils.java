package com.micronet.tellmicronet.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by austin.oneil on 10/8/2018.
 */

public class FileUtils {
    public static String readFileContents(String filePath) throws IOException {
        File file = new File(filePath);
        final InputStream inputStream = new FileInputStream(file);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        final StringBuilder stringBuilder = new StringBuilder();

        boolean done = false;

        while (!done) {
            final String line = reader.readLine();
            done = (line == null);

            if (line != null) {
                stringBuilder.append("\n");
                stringBuilder.append(line);
            }
        }

        reader.close();
        inputStream.close();

        return stringBuilder.toString();
    }

    public static List<File> fileList(String directory) {
        ArrayList<File> fileList = new ArrayList<>();
        File folder = new File(directory);

        File[] listOfFiles = folder.listFiles();
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

    public static List<String> tableList(String sqlitePath) {
        ArrayList<String> tables = new ArrayList<>();
//        File f = new File(sqlitePath);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(sqlitePath, null, SQLiteDatabase.OPEN_READONLY);
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                tables.add( c.getString( c.getColumnIndex("name")) );
                c.moveToNext();
            }
        }
        return tables;
    }

    public static void ZipFiles(HashMap<String, String> filesToBeZipped, String destinationPath) throws IOException {
        final int BUFFER = 1024;
        BufferedInputStream origin = null;
        FileOutputStream dest = new FileOutputStream(destinationPath);
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
    }
}
