package com.example.arafat.bdtraintracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arafat.bdtraintracker.Others.MyActivity;
import com.example.arafat.bdtraintracker.Others.MyInterface;
import com.example.arafat.bdtraintracker.Others.SmsReceiver;
import com.example.arafat.bdtraintracker.Others.Utility;
import com.example.arafat.bdtraintracker.R;

public class MainActivity extends MyActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmsReceiver.bindListener(new MyInterface.SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Utility.popUpReceiveSms(MainActivity.this,messageText);
            }
        });
    }
    public void next(View view){
        Intent intent = new Intent(MainActivity.this,ListTrain.class);
        startActivity(intent);
    }

}
