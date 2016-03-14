package com.happy.test.androidstudy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPwd = (EditText) findViewById(R.id.etPwd);

        Button getPwd = (Button) findViewById(R.id.getPwd);
        getPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = etPwd.getText().toString().trim();
                Toast.makeText(getApplicationContext(), "pwd = " + pwd, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "pwd = " + pwd);
            }
        });
//        setContentView(new WatchView(this));
        File cacheDir = getCacheDir();
        Log.i(TAG, " path = " + cacheDir.getPath() + ", absolutePath = " + cacheDir.getAbsolutePath());

        File imageDir = new File(cacheDir, "image");
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }

        File test1 = new File(imageDir, "hello.txt");
        try {
            if (test1.createNewFile()) {
                Log.i(TAG, "create file success");
            }
            File mFrontImageFile = File.createTempFile("ic_front_image", ".jpg", imageDir);
            File mBackImageFile = File.createTempFile("ic_back_image", ".jpg", imageDir);
            FileOutputStream fos = new FileOutputStream(test1);
            fos.write("hello".getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File filesDir = getFilesDir();
        Log.i(TAG, " path = " + filesDir.getPath() + ", absolutePath = " + filesDir.getAbsolutePath());

    }

}
