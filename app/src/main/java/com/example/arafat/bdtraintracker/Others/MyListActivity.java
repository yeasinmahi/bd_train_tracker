package com.example.arafat.bdtraintracker.Others;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

/**
 * Created by Arafat on 18/03/2017.
 */

public class MyListActivity extends ListActivity{
    protected MyApplication myApplication;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) this.getApplicationContext();
    }

    protected void onResume() {
        super.onResume();
        myApplication.setCurrentActivity(this);
        myApplication.setContext(this);
    }

    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences() {
        Activity currActivity = myApplication.getCurrentActivity();
        if (this.equals(currActivity)){
            myApplication.setCurrentActivity(null);
            myApplication.setContext(this);
        }

    }
}
