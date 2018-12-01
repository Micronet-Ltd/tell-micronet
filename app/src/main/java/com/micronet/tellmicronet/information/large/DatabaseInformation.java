package com.micronet.tellmicronet.information.large;

import com.micronet.tellmicronet.fragments.BaseInformationFragment;
import com.micronet.tellmicronet.fragments.DatabaseFragment;
import com.micronet.tellmicronet.util.Database;
import com.micronet.tellmicronet.util.FileUtils;

import java.util.List;

/**
 * Created by austin.oneil on 9/27/2018.
 */

public class DatabaseInformation extends LargeInformation {
    private String sqliteFilePath;
    protected Database database;
    public DatabaseInformation(String sqliteFilePath) {
        this.sqliteFilePath = sqliteFilePath;
    }

    public DatabaseInformation() {
    }

    @Override
    public BaseInformationFragment generateFragment() {
        if(database==null) database = new Database(sqliteFilePath);
        DatabaseFragment fragment = new DatabaseFragment();
        fragment.setDatabase(database);
        return fragment;
    }

    @Override
    public String extraInfo() {
        if(database==null) database = new Database(sqliteFilePath);
        return database.toString();
    }
}