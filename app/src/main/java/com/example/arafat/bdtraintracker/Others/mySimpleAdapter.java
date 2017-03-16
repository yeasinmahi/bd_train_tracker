package com.example.arafat.bdtraintracker.Others;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arafat.bdtraintracker.R;

import java.util.List;
import java.util.Map;

import static android.R.attr.data;
import static android.R.attr.resource;

/**
 * Created by Arafat on 16/03/2017.
 */

public class mySimpleAdapter extends SimpleAdapter  {

    private final Context context;
    private List<Map<String, Object>> data;
    private int resource;
    private String[] from;
    private int[] to;

    public mySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context=context;
        this.data=(List<Map<String, Object>>) data;
        this.resource=resource;
        this.from=from;
        this.to=to;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View rowView = inflater.inflate(resource, null, true);
        Map<String, Object> medMap=data.get(position);
        final String id = (String) data.get(position).get("id");
        final TextView[] showTv=new TextView[from.length];

        for (int i = 0; i < from.length; i++) {
            showTv[i]=(TextView)rowView.findViewById(to[i]);
            showTv[i].setText(""+medMap.get(from[i]));
        }
        Button sentSmsButton=(Button)rowView.findViewById(R.id.sentSmsButton);
        Button.OnClickListener mOkOnClickListener = new Button.OnClickListener()
        {
            public void onClick(View v) {
                Log.v("ttttttt", ""+showTv[0].getText());
                Toast.makeText(context,""+id, Toast.LENGTH_LONG).show();
            }
        };
        sentSmsButton.setOnClickListener(mOkOnClickListener);

        Button btn2=(Button)rowView.findViewById(R.id.viewDetailsButton);
        Button.OnClickListener mOkOnClickListener2 = new Button.OnClickListener()
        {
            public void onClick(View v) {
                Log.v("hhhhhhh", ""+showTv[0].getText());
                Toast.makeText(context,"abc"+showTv[0].getText(), Toast.LENGTH_LONG).show();
            }
        };
        btn2.setOnClickListener(mOkOnClickListener2);
        return rowView;
    }
}

