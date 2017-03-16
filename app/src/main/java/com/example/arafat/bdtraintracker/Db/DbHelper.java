package com.example.arafat.bdtraintracker.Db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.arafat.bdtraintracker.Model.Train;
import com.example.arafat.bdtraintracker.Others.Utility;

import java.sql.Time;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context context) {
		super(context, Utility.DbName, null, Utility.DbVersion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Train> getAllTrain() {
		ArrayList<Train> trains = new ArrayList<Train>();
		// Rest Index Of Spinner from database
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("train", null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Train train = new Train();
				train.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
				train.setCode(cursor.getString(cursor.getColumnIndex("code")));
				train.setName(cursor.getString(cursor.getColumnIndex("name")));
				train.setOffday(cursor.getString(cursor.getColumnIndex("offDay")));
				train.setFrom(cursor.getString(cursor.getColumnIndex("from")));
				train.setFromTime(Utility.getDate(cursor.getString(cursor.getColumnIndex("fromTime")), Utility.myDateFormat.HH_MM));
				train.setTo(cursor.getString(cursor.getColumnIndex("to")));
				train.setToTime(Utility.getDate(cursor.getString(cursor.getColumnIndex("toTime")), Utility.myDateFormat.HH_MM));
				trains.add(train);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		db.close();
		return trains;
	}

	public ArrayList<Train> loadTrainBySearch(String sql) {
		ArrayList<Train> trains = new ArrayList<Train>();
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Train train = new Train();
				train.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
				train.setCode(cursor.getString(cursor.getColumnIndex("code")));
				train.setOffday(cursor.getString(cursor.getColumnIndex("offDay")));
				train.setFrom(cursor.getString(cursor.getColumnIndex("from")));
				train.setFromTime(Time.valueOf(cursor.getString(cursor.getColumnIndex("fromTime"))));
				train.setTo(cursor.getString(cursor.getColumnIndex("to")));
				train.setToTime(Time.valueOf(cursor.getString(cursor.getColumnIndex("toTime"))));
				trains.add(train);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		db.close();
		return trains;
	}
}
