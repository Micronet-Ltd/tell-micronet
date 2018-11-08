package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.fragments.BaseInformationFragment;
import com.micronet.tellmicronet.util.Database;
import com.micronet.tellmicronet.util.FileUtils;

import java.util.List;

/**
 * Created by austin.oneil on 9/27/2018.
 */

public class DatabaseInformation extends LargeInformation {
    private String sqliteFilePath;
    private Database database;
    public DatabaseInformation(String sqliteFilePath) {
        this.sqliteFilePath = sqliteFilePath;
        database = new Database(sqliteFilePath);
    }

    @Override
    public BaseInformationFragment generateFragment() {

    }

    @Override
    public String extraInfo() {
        StringBuilder sb = new StringBuilder();
        List<String> tables = FileUtils.tableList(sqliteFilePath);
        for (String table : tables) {
            sb.append(FileUtils.tableString(table, sqliteFilePath)).append("\n\n").append("======================").append("\n\n");
        }
        return sb.toString();
    }
}