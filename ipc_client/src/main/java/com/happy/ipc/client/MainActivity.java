package com.happy.ipc.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.happy.ipc.server.IBookManager;
import com.happy.ipc.server.IONewBookArrivedListener;
import com.happy.ipc.server.domain.Book;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MESSAGE_BOOK_ARRIVED = 1;

    private static final String TAG = "MainActivity";

    private Button mGetCount;

    private Button mAddBook;

    private IBookManager mBookManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_BOOK_ARRIVED:
                    Log.i(TAG, "onNewBookArrived : " + msg.obj.toString());
                    break;
            }
        }
    };

    private IONewBookArrivedListener listener = new IONewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookManager = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                mBookManager.registerNewBookArrivedListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
            // 重新连接服务
        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBookManager != null) {
                mBookManager.asBinder().unlinkToDeath(deathRecipient, 0);
                mBookManager = null;
            }
            // 重新连接服务
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetCount = (Button) this.findViewById(R.id.getCount);
        mAddBook = (Button) this.findViewById(R.id.addBook);

        mGetCount.setOnClickListener(this);
        mAddBook.setOnClickListener(this);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.happy.ipc.server", "com.happy.ipc.server.service.BookService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getCount:
                if (mBookManager != null) {
                    try {
                        int count = mBookManager.getBookCount();
                        Log.i(TAG, "count = " + count);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.addBook:
                if (mBookManager != null) {
                    try {
                        List<Book> bookList = mBookManager.addBook(new Book(3, "西游记"));
                        Log.i(TAG, "bookList = " + bookList.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                mBookManager.unRegisterNewBookArrivedListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(conn);
    }
}
