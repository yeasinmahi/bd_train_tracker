package com.example.arafat.bdtraintracker.Others;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;;import com.example.arafat.bdtraintracker.Activity.ListTrain;

/**
 * Created by Arafat on 18/03/2017.
 */

public class SmsReceiver extends BroadcastReceiver{
    private String TAG = SmsReceiver.class.getSimpleName();
    public static MyInterface.SmsListener listener;
    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the data (SMS data) bound to intent
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;

        String str = "";

        if (bundle != null) {
            // Retrieve the SMS Messages received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            // For every SMS message received
            for (int i=0; i < msgs.length; i++) {
                // Convert Object array
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                // Sender's phone number
                str += "SMS from " + msgs[i].getOriginatingAddress() + " : ";
                // Fetch the text message
                str += msgs[i].getMessageBody().toString();
                // Newline <img draggable="false" class="emoji" alt="🙂" src="https://s.w.org/images/core/emoji/72x72/1f642.png">
                str += "\n";
            }
            abortBroadcast();
            Context context1 = ((MyApplication)context.getApplicationContext()).getContext();
            Utility.popUpReceiveSms(context1,str);
            //Log.d(TAG, str);
            if (listener!=null){
                //listener.messageReceived(str);
            }

            // Display the entire SMS Message
            //Utility.popUpReceiveSms(context.getApplicationContext(),str);
        }
    }

    public static void bindListener(MyInterface.SmsListener l) {
        listener = l;
    }
}
