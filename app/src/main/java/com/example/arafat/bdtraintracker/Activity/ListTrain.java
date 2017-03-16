package com.example.arafat.bdtraintracker.Activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.arafat.bdtraintracker.Db.DbHelper;
import com.example.arafat.bdtraintracker.Db.PopulatedOpenHelper;
import com.example.arafat.bdtraintracker.Model.Train;
import com.example.arafat.bdtraintracker.Others.Utility;
import com.example.arafat.bdtraintracker.Others.mySimpleAdapter;
import com.example.arafat.bdtraintracker.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListTrain extends ListActivity{
    private  ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private ImageView statsuImage;
    private Button sentSmsButton,viewDetailsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PopulatedOpenHelper.getInstance(getApplicationContext());
        setContentView(R.layout.activity_list_train);
        Init();
        mySimpleAdapter adapter = new mySimpleAdapter(this, list, R.layout.custom_row_view, new String[] { "name", "place","time"}, new int[] { R.id.nameTextView, R.id.locationTextView,R.id.timeTextView});
        populateList();
        setListAdapter(adapter);

    }

    private void Init() {
        statsuImage= (ImageView) findViewById(R.id.statusImage);
        sentSmsButton = (Button) findViewById(R.id.sentSmsButton);
        viewDetailsButton = (Button) findViewById(R.id.viewDetailsButton);
    }

    private void populateList() {
        DbHelper dbHelper = new DbHelper(getApplicationContext());
        Utility.setTrains(dbHelper.getAllTrain());
        list.clear();
        for (Train train : Utility.getTrains()) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("id", String.valueOf(train.getId()));
            temp.put("name", train.getName());
            temp.put("place", train.getFrom()+"-"+train.getTo());
            temp.put("time", Utility.getDateAsString(train.getFromTime(), Utility.myDateFormat.hh_mm_m)+"-"+Utility.getDateAsString(train.getToTime(), Utility.myDateFormat.hh_mm_m));
            list.add(temp);
        }
    }
}
