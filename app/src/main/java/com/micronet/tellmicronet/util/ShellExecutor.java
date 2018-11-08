package com.micronet.tellmicronet.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by austin.oneil on 6/28/2018.
 */

public class ShellExecutor {

    public ShellExecutor() {

    }

    public String execute(String command) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
            Runtime r = Runtime.getRuntime();

            p = r.exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())); // return to getInputStream

            String line = "";
            while ((line=reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();
        return response;
    }
}