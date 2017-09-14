package com.aaron.applicationtemplate.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper 使用样例
 */
public class SQLiteOpenHelperTest extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES = "create table user(_id integer primary key autoincrement,password varchar(20),email varchar(20))";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS xxx";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public SQLiteOpenHelperTest(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
