package com.micronet.tellmicronet.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by austin.oneil on 11/7/2018.
 */

public class Database {
    HashMap<String, Table> db;

    public Database(String path) {
        db = new HashMap<String, Table>();
        List<String> tableNames = FileUtils.tableList(path);
        for (String tableName : tableNames) {
            db.put(tableName, new Table(path, tableName));
        }
    }

    public Table getTable(String tableName) {
        return db.get(tableName);
    }

    public Set<String> tableNames() {
        return db.keySet();
    }

    public class Table {
        List<HashMap<String, String>> rows;

        public Table(String path, String tableName) {
            rows = new ArrayList<>();

            String[] rowList = FileUtils.shellSqlCommand("SELECT * FROM " + tableName + ";", path).split("\n");
            String[] columns = rowList[0].split("|");

            for(int i=1; i<rowList.length; i++) {
                HashMap<String, String> dataEntries = new HashMap<>();
                String[] data = rowList[i].split("|");
                for(int j=0; j<data.length; j++) {
                    dataEntries.put(columns[j], data[j]);
                }
                rows.add(dataEntries);
            }
        }
    }
}

