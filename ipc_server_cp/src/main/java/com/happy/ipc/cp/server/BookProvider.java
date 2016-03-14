package com.happy.ipc.cp.server;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by zhonglq on 2016/3/14.
 */
public class BookProvider extends ContentProvider {

    private static final String AUTHORITY = "com.happy.ipc.cp.server.book.provider";

    private static final String TAG = "BookProvider";

    private SQLiteDatabase mDB;

    private static final int BOOK_CODE = 1;

    private static final int USER_CODE = 2;

    private Context mContext;

    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, "book", BOOK_CODE);
        mUriMatcher.addURI(AUTHORITY, "user", USER_CODE);
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        initDB();
        return false;
    }

    private void initDB() {
        mDB = new DBOpenHelper(mContext).getReadableDatabase();

        mDB.execSQL("delete from " + DBOpenHelper.BOOK_TABLE);
        mDB.execSQL("delete from " + DBOpenHelper.USER_TABLE);

        mDB.execSQL("insert into " + DBOpenHelper.BOOK_TABLE + " values(1,'三国演义') ");
        mDB.execSQL("insert into " + DBOpenHelper.BOOK_TABLE + " values(2,'红楼梦') ");

        mDB.execSQL("insert into " + DBOpenHelper.USER_TABLE + " values(1,'旺财') ");
        mDB.execSQL("insert into " + DBOpenHelper.USER_TABLE + " values(2,'小强') ");

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String tableName = getTableName(uri);
        return mDB.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long insert = mDB.insert(getTableName(uri), null, values);
        if (insert > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delete = mDB.delete(getTableName(uri), selection, selectionArgs);
        if (delete > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int update = mDB.update(getTableName(uri), values, selection, selectionArgs);
        if (update > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return update;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mUriMatcher.match(uri)) {
            case BOOK_CODE:
                tableName = DBOpenHelper.BOOK_TABLE;
                break;
            case USER_CODE:
                tableName = DBOpenHelper.USER_TABLE;
                break;
            default:
                throw new IllegalArgumentException("无法识别的URI");
        }
        return tableName;
    }

}
