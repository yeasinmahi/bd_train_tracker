package com.gits.traintracker.Db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gits.traintracker.Model.Train;
import com.gits.traintracker.Others.Utility;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {


	private DbHelper(Context context) {
		super(context, Utility.DbName, null, Utility.DbVersion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}
	private static DbHelper instance;
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public static DbHelper getInstance(Context context){
		if(instance==null){
			instance= new DbHelper(context);
		}
		return instance;
	}
	private Train getTrainFromCursor(Cursor cursor){
		Train train = new Train();
		train.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
		train.setCode(cursor.getString(cursor.getColumnIndex("code")));
		train.setName(cursor.getString(cursor.getColumnIndex("name")));
		train.setOffday(cursor.getString(cursor.getColumnIndex("offDay")));
		train.setFrom(cursor.getString(cursor.getColumnIndex("from")));
		train.setFromTime(Utility.getDate(cursor.getString(cursor.getColumnIndex("fromTime")), Utility.myDateFormat.HH_MM));
		train.setTo(cursor.getString(cursor.getColumnIndex("to")));
		train.setToTime(Utility.getDate(cursor.getString(cursor.getColumnIndex("toTime")), Utility.myDateFormat.HH_MM));
		return train;
	}
	 public ArrayList<Train> getAllTrain() {
		ArrayList<Train> trains = new ArrayList<Train>();
		// Rest Index Of Spinner from database
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("train", null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				trains.add(getTrainFromCursor(cursor));
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		db.close();
		return trains;
	}
	public Train getTrainById(int id) {
		Train train = new Train();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from train where id = "+id, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				train = getTrainFromCursor(cursor);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		db.close();
		return train;
	}
	public ArrayList<Train> loadTrainBySearch(String sql) {
		ArrayList<Train> trains = new ArrayList<Train>();
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				trains.add(getTrainFromCursor(cursor));
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		db.close();
		return trains;
	}
}
