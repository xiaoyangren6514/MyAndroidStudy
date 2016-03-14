// IBookManager.aidl
package com.happy.ipc.server;
import com.happy.ipc.server.domain.Book;
import com.happy.ipc.server.IONewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {

   int getBookCount();

   List<Book> addBook(in Book book);

   void registerNewBookArrivedListener(IONewBookArrivedListener listener);

   void unRegisterNewBookArrivedListener(IONewBookArrivedListener listener);

}
