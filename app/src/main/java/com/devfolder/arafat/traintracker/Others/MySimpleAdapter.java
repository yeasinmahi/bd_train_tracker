package com.devfolder.arafat.traintracker.Others;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.devfolder.arafat.traintracker.Activity.SentSms;
import com.devfolder.arafat.traintracker.Db.DbHelper;
import com.devfolder.arafat.traintracker.Model.Train;
import com.devfolder.arafat.traintracker.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Arafat on 16/03/2017.
 */

public class MySimpleAdapter extends SimpleAdapter {

    private final Context context;
    private List<Map<String, Object>> data;
    private int resource;
    private String[] from;
    private int[] to;

    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = (List<Map<String, Object>>) data;
        this.resource = resource;
        this.from = from;
        this.to = to;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        //make view here
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View rowView = inflater.inflate(resource, null, true);
        Map<String, Object> medMap = data.get(position);
        final TextView[] showTv = new TextView[from.length];
        for (int i = 0; i < from.length; i++) {
            showTv[i] = (TextView) rowView.findViewById(to[i]);
            showTv[i].setText("" + medMap.get(from[i]));
        }

        final int id = Integer.parseInt((String) data.get(position).get("id"));
        final Train train = DbHelper.getInstance(context).getTrainById(id);

        //button implementation
        Button sentSmsButton = (Button) rowView.findViewById(R.id.sentSmsButton);
        Button viewDetailsButton = (Button) rowView.findViewById(R.id.viewDetailsButton);
        ImageView trainStatus = (ImageView) rowView.findViewById(R.id.statusImage);
        if (Utility.getTrainStatus(train)){
            Utility.setBackground(context,trainStatus,R.drawable.circle_online_status);
            Utility.setBackground(context,sentSmsButton,R.mipmap.sms_icon);
            sentSmsButton.setEnabled(true);

        }else {
            Utility.setBackground(context,trainStatus,R.drawable.circle_offline_status);
            Utility.setBackground(context,sentSmsButton,R.mipmap.sms_disable_icon);
            sentSmsButton.setEnabled(false);
        }

        sentSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SentSms.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("code", train.getCode());
                context.startActivity(intent);
                //Toast.makeText(context, train.getName(), Toast.LENGTH_LONG).show();
            }

        });
        viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.popUpTrainInfo(context,train);
                //Toast.makeText(context, train.getOffday(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
