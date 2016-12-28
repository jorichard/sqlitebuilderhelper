package com.richard.jo.sqlitebuilder;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class SQLiteBuilderTestDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "test_database";
    private static final int VERSION = 1;
    private List<TableBuilder> tableBuilders;

    public SQLiteBuilderTestDatabase(Context context, List<TableBuilder> tableBuilders){
        super(context, DB_NAME, null, VERSION);
        this.tableBuilders = tableBuilders;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for(TableBuilder tableBuilder:tableBuilders){
            sqLiteDatabase.execSQL(tableBuilder.execute());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON");
    }

    public static String getDbName() {
        return DB_NAME;
    }
}
