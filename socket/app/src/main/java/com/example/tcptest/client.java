package com.example.tcptest;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketAddress;
import java.nio.Buffer;

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
            //new FileSender().execute();
            new ConnectThread().execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    class ConnectThread extends AsyncTask<Void, Void, Void> {
        public int port = 9999;
        public String ip = "15.164.97.173";
        SocketAddress socketAddress;
        Socket fileSock;

        protected Void doInBackground(Void ... voids) {
            try{
                while(true) {
                    socketAddress = new InetSocketAddress(ip, port);
                    fileSock = new Socket();

                    fileSock.connect(socketAddress);

                    get_fileMessage();
                }
            } catch (SocketException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        public void get_fileMessage(){
            try{
                BufferedInputStream bis = new BufferedInputStream(fileSock.getInputStream());
                DataInputStream dis = new DataInputStream(bis);

                String fileName = dis.readUTF();
                System.out.println("Received filename : " + fileName);
                //Handler Download_handler = new Handler();
                //Download_handler.sendEmptyMessage(0);

                File files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Test/" + fileName);

                FileOutputStream fos = new FileOutputStream(files);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                byte[] buf = new byte[4096];

                int c;

                int readdata = 0;

                while((readdata = bis.read(buf)) != -1){
                    bos.write(buf, 0, readdata);
                    bos.flush();
                }
                bos.flush();
                bos.close();

            } catch (Exception e){
                System.out.println("Server Error");
                e.printStackTrace();
            }

        }
    }
}