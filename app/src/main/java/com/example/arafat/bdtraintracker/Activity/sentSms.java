package com.example.arafat.bdtraintracker.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arafat.bdtraintracker.Others.MyActivity;
import com.example.arafat.bdtraintracker.Others.MyInterface;
import com.example.arafat.bdtraintracker.Others.SmsReceiver;
import com.example.arafat.bdtraintracker.Others.Utility;
import com.example.arafat.bdtraintracker.R;

public class SentSms extends MyActivity {
    public static final String ACTION_SMS_SENT = "com.example.android.apis.os.SMS_SENT_ACTION";
    EditText recipientTextEdit = null;
    EditText contentTextEdit =null;
    TextView titleTextView =null;
    String trainCode;
    BroadcastReceiver broadcastReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_sms);
        Init();
        SmsReceiver.bindListener(new MyInterface.SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Utility.popUpReceiveSms(SentSms.this,messageText);
            }
        });
        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
    }

    private void Init() {
        Intent intent = getIntent();
        recipientTextEdit = (EditText)this.findViewById(R.id.editTextEnterReceipents);
        contentTextEdit = (EditText)this.findViewById(R.id.editTextCompose);
        titleTextView = (TextView)this.findViewById(R.id.textViewTitle);
        trainCode = intent.getStringExtra("code");
        recipientTextEdit.setText(Utility.receipentNumber);
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
                titleTextView.setText(message);
                titleTextView.setTextColor(error ? Color.RED : Color.GREEN);

            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter(ACTION_SMS_SENT));
    }

    public void onClickSend(View v)
    {
        //Get recipient from user and check for null
        if (TextUtils.isEmpty(recipientTextEdit.getText())) {
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
        sms.sendTextMessage("2888",null,"FF",PendingIntent.getBroadcast(
                this, 0, new Intent(ACTION_SMS_SENT), 0),null);

        //sms.sendTextMessage("");

        /*//sms body coming from user input
        String strSMSBody = contentTextEdit.getText().toString();
        //sms recipient added by user from the activity screen
        String strReceipentsList = recipientTextEdit.getText().toString();

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