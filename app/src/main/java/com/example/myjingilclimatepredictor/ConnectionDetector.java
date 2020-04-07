package com.example.myjingilclimatepredictor;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class ConnectionDetector {
    final static String CONNECTION_TAG = "CONNECTION_TAG";
    final static String URL_EXAMPLE = "http://www.google.com";

    public static boolean checkInternetJava(){
        boolean connected = false;
        try {
            URL url = new URL(URL_EXAMPLE);
            URLConnection connection = url.openConnection();
            connection.connect();
            connected = true;
            connection.getInputStream().close();
            Log.d(CONNECTION_TAG, "Internet is connected");
        } catch (MalformedURLException e) {
            Log.d(CONNECTION_TAG, "Internet is not connected");
        } catch (IOException e) {
            Log.d(CONNECTION_TAG, "Internet is not connected");
        }
       return connected;
    }
    public static boolean checkInternetByIP(){
        try {
                InetAddress address = InetAddress.getByName("www.google.com");
                return address.isReachable(3000);

        } catch (UnknownHostException e) {
            Log.d(CONNECTION_TAG, "error in checking net availability");
        } catch (IOException e) {
            Log.d(CONNECTION_TAG, "error in checking net availability");
        }
        return false;
    }
}
