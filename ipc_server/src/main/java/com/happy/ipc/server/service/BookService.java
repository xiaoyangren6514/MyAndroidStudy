package com.happy.ipc.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.happy.ipc.server.IBookManager;
import com.happy.ipc.server.IONewBookArrivedListener;
import com.happy.ipc.server.domain.Book;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookService extends Service {

    private static final String TAG = "BookService";

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IONewBookArrivedListener> listenerList = new RemoteCallbackList<>();

    public BookService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1, "红楼梦"));
        bookList.add(new Book(2, "三国演义"));
        new Thread(new WorkService()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
//        int check = checkCallingOrSelfPermission("com.happ.ipc.server.ACCESS_BOOK_SERVICE");
//        if (check == PackageManager.PERMISSION_DENIED) {
//            Log.i(TAG, "permission denied");
//            return null;
//        }
//        Log.i(TAG, "permission access");
        return mBinder;
    }

    private IBinder mBinder = new IBookManager.Stub() {
        @Override
        public int getBookCount() throws RemoteException {
            return bookList.size();
        }

        @Override
        public List<Book> addBook(Book book) throws RemoteException {
            bookList.add(book);
            return bookList;
        }

        @Override
        public void registerNewBookArrivedListener(IONewBookArrivedListener listener) throws RemoteException {
            listenerList.register(listener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Log.i(TAG, "listenerList size = " + listenerList.getRegisteredCallbackCount());
            }
        }

        @Override
        public void unRegisterNewBookArrivedListener(IONewBookArrivedListener listener) throws RemoteException {
            listenerList.unregister(listener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Log.i(TAG, "listenerList size = " + listenerList.getRegisteredCallbackCount());
            }
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            int callingPid = getCallingPid();
            int callingUid = getCallingUid();
            Log.i(TAG, "callingPid = " + callingPid + ",callingUid = " + callingUid);
            String[] packagesForUid = BookService.this.getPackageManager().getPackagesForUid(callingUid);
            if (packagesForUid != null && packagesForUid.length > 0) {
                packageName = packagesForUid[0];
            }
            Log.i(TAG, "packageName = " + packageName);
            if (TextUtils.isEmpty(packageName) || !"com.happy.ipc.client.test".equals(packageName)) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    private class WorkService implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Book book = new Book(bookList.size() + 1, "new book" + bookList.size());
                try {
                    onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        bookList.add(book);
        int N = listenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IONewBookArrivedListener arrivedListener = listenerList.getBroadcastItem(i);
            if (arrivedListener != null) {
                arrivedListener.onNewBookArrived(book);
            }
        }
        listenerList.finishBroadcast();
    }

}
