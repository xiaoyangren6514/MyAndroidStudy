package com.happy.ipc.cp.client;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //以book查询为例
        Uri uri = Uri.parse("content://com.happy.ipc.cp.server.book.provider/book");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String bookName = cursor.getString(1);
                Log.i(TAG, " id = " + id + " ,bookName = " + bookName);
            }
            cursor.close();
        }
    }
}
