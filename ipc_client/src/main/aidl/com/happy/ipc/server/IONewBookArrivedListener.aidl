// IONewBookArrivedListener.aidl
package com.happy.ipc.server;
import com.happy.ipc.server.domain.Book;
// Declare any non-default types here with import statements

interface IONewBookArrivedListener {

   void onNewBookArrived(in Book book);

}
