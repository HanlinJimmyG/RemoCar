package com.example.remocar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer timer=new Timer();//Timer类是JDK中提供的一个定时器功能，使用时会在主线程之外开启一个单独的线程执行指定任务，任务可以执行一次或者多次
        //TimerTask实现runnable接口，TimerTask类表示在一个指定时间内执行的task
        TimerTask task=new TimerTask() {
            @Override
            public void run() {//跳转主界面的任务代码写在TimerTask的run()方法中
                startActivity(new Intent(MainActivity.this, WebConnection.class));
            }
        };
        timer.schedule(task,1000);
    }
}