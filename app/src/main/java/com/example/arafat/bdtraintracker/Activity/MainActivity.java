package com.example.arafat.bdtraintracker.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arafat.bdtraintracker.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void next(View view){
        Intent intent = new Intent(MainActivity.this,ListTrain.class);
        startActivity(intent);
    }

}
