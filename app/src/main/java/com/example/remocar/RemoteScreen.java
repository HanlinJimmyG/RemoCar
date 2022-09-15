package com.example.remocar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remocar.VE.CarStatus;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class RemoteScreen extends AppCompatActivity {

    private static final String TAG = "my_RemoteScreen";
    protected CarStatus carStatus = new CarStatus(0,0,10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remote_screen);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String ip = (String) bundle.get("ip");
        int port = (int) bundle.get("port");

        Log.d(TAG, "onCreate: "+ip+port);
        TCPThread tcpThread = new TCPThread(ip,port);

        // 绑定按钮
        Button b_start = (Button) findViewById(R.id.b_start);
        Button b_L = (Button) findViewById(R.id.b_left);
        Button b_R = (Button) findViewById(R.id.b_right);
        Button b_G = (Button) findViewById(R.id.b_go);
        Button b_B = (Button) findViewById(R.id.b_back);
        SeekBar seekbar = (SeekBar) findViewById(R.id.br_spped);
        TextView barTittle = (TextView) findViewById(R.id.t_barTittle);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                barTittle.setText("小车速度："+i+"%");
                carStatus.setSpeed(i);
                tcpThread.setCarStatus(carStatus);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        b_start.setVisibility(View.VISIBLE);
        b_start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "onTouch: tcp touched");
                        new Thread(tcpThread).start();

                        Context context = getApplicationContext();
                        CharSequence text;
                        int duration = Toast.LENGTH_SHORT;

                        text = "连接中。。。";
                        Toast.makeText(context, text, duration).show();

                        for (;;){
                            Log.d(TAG, "onTouch: aaaaaaaaaaaaaa");
                            if (tcpThread.socketStatus()){
                                text = "网络已连接";
                                Toast.makeText(context, text, duration).show();
                                break;
                            }
                        }




                        b_start.setVisibility(View.GONE);
                        break;
                }

                Log.d(TAG, "tcpThread start");
                return false;
            }
        });


        Log.d(TAG, "onCreate: before touch");
        // 左键控制
        b_L.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(TAG, "onTouch: into touch");
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        carStatus.minusDir();
                        tcpThread.setCarStatus(carStatus);
                        break;
                    case MotionEvent.ACTION_UP:
                        carStatus.addDir();
                        tcpThread.setCarStatus(carStatus);
                }
                Log.d(TAG, ""+carStatus.getDir()+carStatus.getStatus()+carStatus.getSpeed());
                return false;
            }
        });

        // 右键控制
        b_R.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        carStatus.addDir();
                        tcpThread.setCarStatus(carStatus);
                        break;
                    case MotionEvent.ACTION_UP:
                        carStatus.minusDir();
                        tcpThread.setCarStatus(carStatus);
                }
                Log.d(TAG, ""+carStatus.getDir()+carStatus.getStatus()+carStatus.getSpeed());
                return false;
            }
        });

        // 前进键控制
        b_G.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        carStatus.addStatus();
                        tcpThread.setCarStatus(carStatus);
                        break;
                    case MotionEvent.ACTION_UP:
                        carStatus.minusStatus();
                        tcpThread.setCarStatus(carStatus);
                }
                Log.d(TAG, ""+carStatus.getDir()+carStatus.getStatus()+carStatus.getSpeed());
                return false;
            }
        });

        // 后退键控制
        b_B.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        carStatus.minusStatus();
                        tcpThread.setCarStatus(carStatus);
                        break;
                    case MotionEvent.ACTION_UP:
                        carStatus.addStatus();
                        tcpThread.setCarStatus(carStatus);
                }
                Log.d(TAG, ""+carStatus.getDir()+carStatus.getStatus()+carStatus.getSpeed());
                return false;
            }
        });
    }
}