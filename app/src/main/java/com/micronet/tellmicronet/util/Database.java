package com.micronet.tellmicronet.util;

import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by austin.oneil on 11/7/2018.
 */

public class Database {
    HashMap<String, Table> db;

    public Database(HashMap<String, Cursor> cursors) {
        db = new HashMap<String, Table>();
        for (String tableName : cursors.keySet()) {
            db.put(tableName, new Table(cursors.get(tableName)));
        }
    }

    public Database(String path) {
        db = new HashMap<String, Table>();
        List<String> tableNames = FileUtils.tableList(path);
        for (String tableName : tableNames) {
            db.put(tableName, new Table(path, tableName));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String tableName : db.keySet()) {
            sb.append(tableName).append("\n\n").append(db.get(tableName).toString()).append("======================").append("\n\n");
        }
        return sb.toString();
    }

    public Table getTable(String tableName) {
        return db.get(tableName);
    }

    public Set<String> tableNames() {
        return db.keySet();
    }

    public class Table {
        List<HashMap<String, String>> rows;

        public Table(Cursor cursor) {
            rows = new ArrayList<>();
            String[] columns = cursor.getColumnNames();

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                HashMap<String, String> row = new HashMap<>();
                for (String column : columns) {
                    String data = cursor.getString(cursor.getColumnIndex(column));
                    row.put(column, data);
                }
                rows.add(row);
                cursor.moveToNext();
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Object[] columns = getColumns().toArray();
            for (Object column : columns) {
                sb.append((String)column).append("\t");
            }
            sb.append("\n");
            for (HashMap<String, String> row : getRows()) {
                for (Object column : columns) {
                    sb.append(row.get(column)).append("\t");
                }
                sb.append("\n");
            }
            return sb.toString();
        }

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

