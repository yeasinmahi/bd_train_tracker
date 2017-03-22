package com.example.arafat;

import android.Manifest;
import android.app.Application;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;

import com.example.arafat.bdtraintracker.Activity.MainActivity;
import com.example.arafat.bdtraintracker.Activity.SentSms;
import com.example.arafat.bdtraintracker.Others.MyApplication;

/**
 * Created by Arafat on 22/03/2017.
 */
public class ServiceStarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ActivityCompat.requestPermissions(new MainActivity(), new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, 1);

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
