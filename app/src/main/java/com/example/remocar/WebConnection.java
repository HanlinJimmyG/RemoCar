package com.example.remocar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class WebConnection extends AppCompatActivity {

    private static final String TAG = "WebConnection";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: web");
        setContentView(R.layout.activity_web_connection);

        EditText et_ip = (EditText) findViewById(R.id.et_ServAddr);
        EditText et_port = (EditText) findViewById(R.id.et_ServPort);

        final Button b_welcome = (Button)findViewById(R.id.b_connect);
        b_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WebConnection.this, RemoteScreen.class);

                String ip = et_ip.getText().toString();
                String port = et_port.getText().toString();

                Context context = getApplicationContext();
                CharSequence text = "";
                int duration = Toast.LENGTH_SHORT;

                if (!Tools.isRightIP(ip)){
                    text = "IP地址格式错误";
                    Toast.makeText(context, text, duration).show();
                } else if (!Tools.isRightPort(port)){
                    text = "端口号格式错误";
                    Toast.makeText(context, text, duration).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("ip",ip);
                    bundle.putInt("port",Integer.parseInt(port));

                    intent.putExtras(bundle);

                    startActivity(intent);
                }
            }
        });
    }
}