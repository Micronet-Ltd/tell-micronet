package com.micronet.tellmicronet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by austin.oneil on 9/27/2018.
 */

public class DatabaseInformation extends LargeInformation {
    private String sqliteFilePath;
    public DatabaseInformation(String sqliteFilePath) {
        this.sqliteFilePath = sqliteFilePath;
    }

    @Override
    protected String extraInfo() {

    }

    private ArrayList<String> tableList(SQLiteDatabase db) {
        ArrayList<String> tableNames = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                tableNames.add( c.getString( c.getColumnIndex("name")) );
                c.moveToNext();
            }
        }
        return tableNames;
    }
}
