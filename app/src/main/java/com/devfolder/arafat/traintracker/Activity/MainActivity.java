package com.devfolder.arafat.traintracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.devfolder.arafat.traintracker.Others.MyActivity;
import com.devfolder.arafat.traintracker.Others.Utility;
import com.devfolder.arafat.traintracker.R;

public class MainActivity extends MyActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        if (message!=null&&!message.equals("")){

            Utility.popUpReceiveSms(this,message,true);
        }
    }
    public void next(View view){
        Intent intent = new Intent(MainActivity.this,ListTrain.class);
        startActivity(intent);
    }

}
