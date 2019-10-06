package com.example.socket_sj;


import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketAddress;

public class client extends Thread {
    private static final String serverIP = "15.164.97.173";
    private static final int serverPort = 9999;
    private Socket socket;

    public client() {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Test");
        if (!f.exists()) {
            f.mkdirs();

        }
    }
    public void run(){
        System.out.println("run");
        try{
            new FileSender().execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private class FileSender extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void ... voids) {
            try{
                System.out.println("address creation started");
                SocketAddress serverAddress = new InetSocketAddress(serverIP, serverPort);
                //socket = new Socket(serverIP, serverPort);
                System.out.println("socket creation started");
                socket = new Socket();
                Log.d("tag", "socket created");
                System.out.println("socket created");
                socket.connect(serverAddress);

                DataInputStream dis = new DataInputStream(new FileInputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Test"), "/test.txt")));
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                byte[] buf = new byte[1024];

                long totalReadBytes = 0;
                int readBytes;

                while((readBytes = dis.read(buf)) > 0){
                    dos.write(buf, 0, readBytes);
                    totalReadBytes += readBytes;
                }
                dos.close();
            } catch (IOException ie){
                ie.printStackTrace();
                Log.d("tag", "failed");
                System.out.println("failed");
            }
            return null;
        }
    }
}