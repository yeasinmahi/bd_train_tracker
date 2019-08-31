package com.gits.arafat.traintracker.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gits.arafat.traintracker.Others.MyActivity;
import com.gits.arafat.traintracker.Others.Utility;
import com.gits.arafat.traintracker.R;

public class SentSms extends MyActivity {
    public static final String ACTION_SMS_SENT = "com.gits.android.apis.os.SMS_SENT_ACTION";
    EditText sendingTextEdit = null;
    EditText contentTextEdit =null;
    TextView titleTextView =null;
    TextView warningEditText =null;
    String trainCode;
    BroadcastReceiver broadcastReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_sms);
        Init();

        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
    }

    private void Init() {
        Intent intent = getIntent();
        sendingTextEdit = (EditText)this.findViewById(R.id.editTextEnterReceipents);
        contentTextEdit = (EditText)this.findViewById(R.id.editTextCompose);
        titleTextView = (TextView)this.findViewById(R.id.textViewTitle);
        warningEditText = (TextView)this.findViewById(R.id.warningEditText);
        trainCode = intent.getStringExtra("code");
        sendingTextEdit.setText(Utility.SenderNumber);
        contentTextEdit.setText(Utility.getSmsBody(trainCode));
        // Register broadcast receivers for SMS sent and delivered intents
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = null;
                boolean error = true;
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        message = "Message sent!";
                        error = false;
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        message = "Error: check balance or sms setting";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        message = "Error: No Sim card available.";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        message = "Error: Null PDU.";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        message = "Error: Radio off.";
                        break;
                }
                warningEditText.setText(message);
                warningEditText.setTextColor(error ? Color.RED : Color.parseColor("#466F84"));

            }
        };
        ActivityCompat.requestPermissions(SentSms.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        registerReceiver(broadcastReceiver,new IntentFilter(ACTION_SMS_SENT));
    }

    public void onClickSend(View v)
    {
        //Get recipient from user and check for null
        if (TextUtils.isEmpty(sendingTextEdit.getText())) {
            titleTextView.setText("Enter Receipent");
            titleTextView.setTextColor(Color.RED);
            return;
        }

        //Get content and check for null
        if (TextUtils.isEmpty(contentTextEdit.getText())) {
            titleTextView.setText("Empty Content");
            titleTextView.setTextColor(Color.RED);
            return;
        }

        SmsManager sms = SmsManager.getDefault();
        /*sms.sendTextMessage(Utility.receipentNumber,null,Utility.getSmsBody(trainCode),PendingIntent.getBroadcast(
                this, 0, new Intent(ACTION_SMS_SENT), 0),null);*/
        sms.sendTextMessage(Utility.SenderNumber,null,Utility.getSmsBody(trainCode),PendingIntent.getBroadcast(
                this, 0, new Intent(ACTION_SMS_SENT), 0),null);

        //sms.sendTextMessage("");

        /*//sms body coming from user input
        String strSMSBody = contentTextEdit.getText().toString();
        //sms recipient added by user from the activity screen
        String strReceipentsList = sendingTextEdit.getText().toString();

        List<String> messages = sms.divideMessage(strSMSBody);
        for (String message : messages) {
            sms.sendTextMessage(strReceipentsList, null, message, PendingIntent.getBroadcast(
                    this, 0, new Intent(ACTION_SMS_SENT), 0), null);

        }*/
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        try{
            if(broadcastReceiver!=null) {
                unregisterReceiver(broadcastReceiver);
            }
        }catch(Exception e)
        {

        }
        super.onDestroy();

    }
}
