package com.happy.ipc.cp.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhonglq on 2016/3/14.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "demo.db";
    private static final int DB_VERSION = 1;

    public static final String BOOK_TABLE = "book";
    public static final String USER_TABLE = "user";

    private String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE + " (" +
            "_id INTEGER PRIMARY KEY , name TEXT)";

    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" +
            "_id INTEGER PRIMARY KEY , name TEXT)";

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
