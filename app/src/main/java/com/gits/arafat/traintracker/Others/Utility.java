package com.gits.arafat.traintracker.Others;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gits.arafat.traintracker.Model.Train;
import com.gits.arafat.traintracker.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Arafat on 03/03/2017.
 */

public class Utility {
    public static final String DbName = "btt.db";
    public static int DbVersion = 1;
    public static String SenderNumber = "16318";
    public static String ReceipentNumber = "16318";
    public static String getSmsBody(String code){
        return "TR "+code;
    }
    public static Date ErrorDate = new Date();
    static {
        try {
            ErrorDate = (new SimpleDateFormat(myDateFormat.yyyy_MM_dd.toString())).parse("2000-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public enum myDateFormat {
        dd_MMM_yyyy("dd-MMM-yyyy"),
        yyyy_MM_dd("yyyy-MM-dd"),
        MMM_yyyy("MMM-yyyy"),
        HH_MM("HH:MM"),
        hh_mm_m("hh:mm a"),
        MMM_dd_h_mm_m("MMM dd, h:mm a");

        private final String text;

        private myDateFormat(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }
    public static Date getDate(String date, myDateFormat dateDFormat){
        DateFormat format = new SimpleDateFormat(dateDFormat.toString());
        Date myDate;
        try {
            myDate= format.parse(date);
            return myDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ErrorDate;
    }
    public static String getDateAsString(Date date,Utility.myDateFormat dateFormat){
        return new SimpleDateFormat(dateFormat.toString()).format(date);
    }
    public static JSONArray convertStringToJson(String s) {
        try {
            return new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList getRandomizationList(ArrayList list) {
        Collections.shuffle(list, new Random(System.nanoTime()));
        return list;
    }
    public static boolean isEmpty(EditText editText){
        return editText.getText().equals("");
    }
    public static void clearAll(ViewGroup root) {
        for (int j = root.getChildCount(); j >0; j--) {
            View view = root.getChildAt(j);
            if (view instanceof ViewGroup) {
                clearAll((ViewGroup) view);
                continue;
            }
            if (view instanceof EditText) {
                ((EditText) view).setText("");
                continue;
            }
        }
    }

    public static ArrayList<Train> getTrains() {
        return trains;
    }

    public static void setTrains(ArrayList<Train> trains) {
        Utility.trains = trains;
    }

    private static ArrayList<Train> trains=null;

    public static void popUpTrainInfo(final Context context, final Train train) {
        final Dialog dialog = new Dialog(context,R.style.MyTheme);
        dialog.setContentView(R.layout.custom_dialog_train_info);

        final TextView trainName = (TextView) dialog.findViewById(R.id.trainNameTextView);
        final TextView trainCode = (TextView) dialog.findViewById(R.id.trainCodeTextView);
        final TextView from = (TextView) dialog.findViewById(R.id.fromTextView);
        final TextView to = (TextView) dialog.findViewById(R.id.toTextView);
        final TextView fromTime = (TextView) dialog.findViewById(R.id.fromTimeTextView);
        final TextView toTime = (TextView) dialog.findViewById(R.id.toTimeTextView);
        final TextView offDay = (TextView) dialog.findViewById(R.id.offDayTextView);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        trainName.setText(train.getName());
        trainCode.setText("("+train.getCode()+")");
        from.setText(train.getFrom());
        to.setText(train.getTo());
        fromTime.setText(Utility.getDateAsString(train.getFromTime(),myDateFormat.hh_mm_m));
        toTime.setText(Utility.getDateAsString(train.getToTime(),myDateFormat.hh_mm_m));
        offDay.setText("Off Day: "+train.getOffday());
        trainName.setText(train.getName());
        dialog.show();

    }
    public static void popUpReceiveSms(final Context context, final String message, final boolean haveToClose) {
        final Dialog dialog = new Dialog(context,R.style.MyTheme);
        dialog.setContentView(R.layout.custom_dialog_receive_sms);
        final TextView smsHeaderTextView = (TextView) dialog.findViewById(R.id.smsHeaderTextView);
        final TextView messageTextView = (TextView) dialog.findViewById(R.id.messageTextView);
        final TextView smsTimeTextView = (TextView) dialog.findViewById(R.id.smsTimeTextView);
        Date time = new Date();
        smsHeaderTextView.setText(Utility.ReceipentNumber);
        smsTimeTextView.setText(getDateAsString(time,myDateFormat.MMM_dd_h_mm_m));
        messageTextView.setText(message);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (haveToClose){
                    System.exit(0);
                }
                dialog.dismiss();
            }
        });
        Activity currentActivity = ((MyApplication)context.getApplicationContext()).getCurrentActivity();

        if(!((Activity) context).isFinishing())
        {
            dialog.show();
        }

    }
    private static String today=null;
    private static String getDayOfNow(){
        if (today==null){
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            today= new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        }
        return today;
    }
    private static Calendar getCalenderFromTime(Date time){
        Calendar c =Calendar.getInstance();
        c.setTime(time);
        c.set(1990,01,01);
        return c;
    }
    private static boolean isBetweenTime(Date fTime,Date tTime){
        Calendar c1 = getCalenderFromTime(fTime);
        Calendar c2= getCalenderFromTime(tTime);
        c2.add(Calendar.HOUR, 1);
        Calendar c3 = getCalenderFromTime(new Date());
        //c3.add(Calendar.DATE, 1);

        Date currentTime = c3.getTime();
        Date fromTime = c1.getTime();
        Date toTime = c2.getTime();
        if (fromTime.after(toTime)){
            c1.add(Calendar.DATE,-1);
            fromTime = c1.getTime();
        }
        if (currentTime.after(fromTime) && currentTime.before(toTime))
            return true;
        return false;
    }
    public static boolean getTrainStatus(Train train){
        String day = getDayOfNow();
        if (!day.equals(train.getOffday())){
            boolean isBetween= isBetweenTime(train.getFromTime(),train.getToTime());
            if (isBetween){
                return true;
            }
            return false;
        }
        return false;
    }
    public static void setBackground(Context context,View view,int id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(ContextCompat.getDrawable(context, id));
        }else {
            view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_online_status));
        }
    }
    public static String test(ArrayList<Train> list,int id){
        //Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
       for (Train train:list){
           if(train.getId()==id){
               return train.getName();
           }
       }
        return null;
    }


}

