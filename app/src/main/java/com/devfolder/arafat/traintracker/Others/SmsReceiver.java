package com.devfolder.arafat.traintracker.Others;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

;

/**
 * Created by Arafat on 18/03/2017.
 */

public class SmsReceiver extends BroadcastReceiver{
    private String TAG = SmsReceiver.class.getSimpleName();
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
            String sender = null;
            // For every SMS message received
            for (int i=0; i < msgs.length; i++) {
                // Convert Object array
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                // Sender's phone number
                sender = msgs[i].getOriginatingAddress();
                // Fetch the text message
                str += msgs[i].getMessageBody().toString();
                // Newline <img draggable="false" class="emoji" alt="🙂" src="https://s.w.org/images/core/emoji/72x72/1f642.png">
                str += "\n";
            }
            if (sender.contains(Utility.ReceipentNumber.toString())) {
                abortBroadcast();
                Context context1 = ((MyApplication)context.getApplicationContext()).getContext();

                if (context1!=null){
                    Utility.popUpReceiveSms(context1,str, false);
                }else {
                    try {
                        Intent startIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                        startIntent.setFlags(
                                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT |
                                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                        );
                        startIntent.putExtra("message",str);
                        context.startActivity(startIntent);

                    }catch (Exception ex){

                    }
                }
            }
        }
    }
}
