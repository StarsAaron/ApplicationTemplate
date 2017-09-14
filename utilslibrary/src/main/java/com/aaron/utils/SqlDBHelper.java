package com.aaron.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper 使用例子
 */
public class SqlDBHelper extends SQLiteOpenHelper {
    private static final String CREATE_READ_TABLE = "create table read_record(_id integer primary key autoincrement,newsid varchar(20))";
    private static SqlDBHelper dbHelper;
    private static final String DATABASE_NAME = "Test";
    private static final int DATABASE_VERSION = 1;

    public SqlDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_READ_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SqlDBHelper getDBHelper(Context context) {
        if (dbHelper == null) {
            synchronized (SqlDBHelper.class) {
                dbHelper = new SqlDBHelper(context);
            }
        }
        return dbHelper;
    }
}
