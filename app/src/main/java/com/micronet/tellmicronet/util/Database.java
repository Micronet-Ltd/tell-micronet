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

        public Set<String> getColumns() {
            return rows.get(0).keySet();
        }

        public List<HashMap<String, String>> getRows() {
            return rows;
        }

        public Table(String path, String tableName) {
            rows = new ArrayList<>();

            String[] rowList = FileUtils.tableString(tableName, path).split("\n");
            List<String> columns = FileUtils.columns(path, tableName);

            for(String row : rowList) {
                HashMap<String, String> dataEntries = new HashMap<>();
                String[] data = row.split("\\|");
                for(int j=0; j<data.length; j++) {
                    dataEntries.put(columns.get(j), data[j]);
                }
                rows.add(dataEntries);
            }
        }
    }
}

