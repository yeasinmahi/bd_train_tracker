package com.example.arafat.bdtraintracker.Others;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.StaticLayout;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arafat.bdtraintracker.Db.DbHelper;
import com.example.arafat.bdtraintracker.Model.Train;
import com.example.arafat.bdtraintracker.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 * Created by Arafat on 03/03/2017.
 */

public class Utility {
    public static final String DbName = "btt.db";
    public static int DbVersion = 1;
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
        hh_mm_m("hh:mm a");

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
        try {
            return format.parse(date);
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

    public static void popUpWindow(final Context context, final Train train) {
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


}

