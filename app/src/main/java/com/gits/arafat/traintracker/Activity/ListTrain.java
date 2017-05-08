package com.gits.arafat.traintracker.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.gits.arafat.traintracker.Db.DbHelper;
import com.gits.arafat.traintracker.Db.PopulatedOpenHelper;
import com.gits.arafat.traintracker.Model.Train;
import com.gits.arafat.traintracker.Others.MyListActivity;
import com.gits.arafat.traintracker.Others.MySimpleAdapter;
import com.gits.arafat.traintracker.Others.Utility;
import com.gits.arafat.traintracker.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListTrain extends MyListActivity{
    private  ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private ImageView statsuImage;
    private Button sentSmsButton,viewDetailsButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PopulatedOpenHelper.getInstance(getApplicationContext());
        setContentView(R.layout.activity_list_train);
        Init();
        MySimpleAdapter adapter = new MySimpleAdapter(this, list, R.layout.custom_row_view, new String[] { "name"}, new int[] { R.id.nameTextView});
        populateList();
        setListAdapter(adapter);

    }

    private void Init() {
        statsuImage= (ImageView) findViewById(R.id.statusImage);
        sentSmsButton = (Button) findViewById(R.id.sentSmsButton);
        viewDetailsButton = (Button) findViewById(R.id.viewDetailsButton);
    }

    private void populateList() {
        Utility.setTrains(DbHelper.getInstance(getApplicationContext()).getAllTrain());
        list.clear();
        for (Train train : Utility.getTrains()) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("id", String.valueOf(train.getId()));
            temp.put("online", String.valueOf(Utility.getTrainStatus(train)));
            temp.put("name", train.getName());
            temp.put("place", train.getFrom()+"-"+train.getTo());
            temp.put("time", Utility.getDateAsString(train.getFromTime(), Utility.myDateFormat.hh_mm_m)+"-"+Utility.getDateAsString(train.getToTime(), Utility.myDateFormat.hh_mm_m));
            list.add(temp);
        }
    }
}
