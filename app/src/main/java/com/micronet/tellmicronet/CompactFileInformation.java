package com.micronet.tellmicronet;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
            return readFileContents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFileContents() throws IOException {
        File file = new File(filePath);
        final InputStream inputStream = new FileInputStream(file);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        final StringBuilder stringBuilder = new StringBuilder();

        boolean done = false;

        while (!done) {
            final String line = reader.readLine();
            done = (line == null);

            if (line != null) {
                stringBuilder.append(line);
            }
        }

        reader.close();
        inputStream.close();

        return stringBuilder.toString();
    }
}
