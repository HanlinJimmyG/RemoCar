package com.example.remocar;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class UDPThread implements Runnable{
    private static final String TAG = "my_UDPThread";
    @Override
    public void run() {
        try {
            Log.d(TAG, "UDPThread run: udp start:");

            DatagramSocket ds = new DatagramSocket(8080);
            ByteBuffer buf = ByteBuffer.allocate(1024);
            DatagramPacket dp = new DatagramPacket(buf.array(),buf.capacity());
            Log.d(TAG, "UDPThread run: udp ready ");
            for (;;){
                ds.receive(dp);
                Log.d(TAG, "UDPThread run: udp received");
                String string = buf.array().toString();
                Log.d(TAG, "run: mas: "+string);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
