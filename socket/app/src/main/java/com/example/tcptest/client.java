package com.example.tcptest;

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
        try{
            AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    try{
                        SocketAddress serverAddress = new InetSocketAddress(serverIP, serverPort);
                        //socket = new Socket(serverIP, serverPort);
                        socket = new Socket();
                        Log.d("tag", "socket created");
                        socket.connect(serverAddress);

                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

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
                    }
                    return null;
                }
            };
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

/*class FileSender extends Thread {
    Socket socket;
    PrintWriter printwriter;
    DataInputStream dis;
    DataOutputStream dos;

    String filename;
    int control = 0;

    public FileSender(Socket socket, String filestr){
        this.socket = socket;
        this.filename = filestr;
    }

    public void run() {
         try{
             this.printwriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
             this.dis = new DataInputStream(new FileInputStream(new File(Environment.getExternalStorageDirectory(), filename)));
             this.dos = new DataOutputStream(socket.getOutputStream());

             byte[] buf = new byte[1024];

             long totalReadBytes = 0;
             int readBytes;

             while((readBytes = dis.read(buf)) > 0){
                 dos.write(buf, 0, readBytes);
                 totalReadBytes += readBytes;
             }
             dos.close();

             System.out.println("Complete");
         } catch(IOException e){
             e.printStackTrace();
         }
    }
}*/