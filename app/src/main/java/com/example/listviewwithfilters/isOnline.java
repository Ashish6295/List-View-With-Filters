package com.example.listviewwithfilters;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class isOnline extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... strings) {

        try {

            int timeOut = 2000;

            Socket socket = new Socket();

            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);

            socket.connect(socketAddress,timeOut);

            socket.close();

            return true;

        }catch (IOException e){
            return false;
        }
    }
}
