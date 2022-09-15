package com.example.remocar;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.remocar.VE.CarStatus;

import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

public class TCPThread implements Runnable {
    private String ip = "";
    private int port = 0;
    private int tick =0;

    private Socket socket;

    public TCPThread(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public boolean socketStatus(){return socket!=null && socket.isConnected();}


    private CarStatus carStatus = new CarStatus(0,0,10);

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus.updateStatus(carStatus);
    }

    @Override
    public void run() {
        try {
            socket = new Socket(ip,port);
            //获取Socket的输出输入流
            PrintStream ps = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//           while (true){
//               try {
//                   this.sleep(1000);
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
//               tick++;
//               Log.d("Awwwwwwwww", ""+this.carStatus.getDir()+this.carStatus.getStatus()+this.carStatus.getSpeed());
//               Log.d("Awwwwwwwww", "tick:"+tick);
//               ps.println(""+this.carStatus.getDir()+this.carStatus.getStatus()+this.carStatus.getSpeed());
//               ps.println("tick:"+tick);
//               //ps.flush();
//            }

            CarStatus tempStaus = new CarStatus(carStatus);
            while (true){
                if (!tempStaus.sameStatus(carStatus)){
                    ps.println(carStatus.jsonMsg().toString());
                    ps.flush();
                    tempStaus.updateStatus(carStatus);
                }
            }

    } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}
