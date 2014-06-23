package com.example.mission;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MissionActivity extends Activity implements OnClickListener{
	private Button btnOK;
	private Button btnDate;
	private Button btnTime;
	
	private TextView name;
	private TextView task;
	int GMT = 3;
	int myYear;
	int myMonth;
	int myDay;
	
	String nameP;
	String taskP;
	String dateP;
	String timeP;
	long now;
	
	int currentDate;
	
	int myHour;
	int myMinute;
	  
	int DIALOG_DATE = 1;
	int DIALOG_TIME = 2;
	
	Calendar c;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mission);
		
		c = Calendar.getInstance();
		myDay = c.get(Calendar.DAY_OF_MONTH);
		myMonth = c.get(Calendar.MONTH);
		myYear = c.get(Calendar.YEAR);
		
		myHour = c.get(Calendar.HOUR_OF_DAY) + GMT;
		myMinute = c.get(Calendar.MINUTE);
		
		now = c.getTimeInMillis();
		
		currentDate = myYear * 364 + myMonth * 30 + myDay;
        btnOK = (Button) findViewById(R.id.addNewMission);
		btnDate = (Button) findViewById(R.id.dateMission);
		btnTime = (Button) findViewById(R.id.timeMission);
		
		btnTime.setOnClickListener(this);
		btnDate.setOnClickListener(this);
		btnOK.setOnClickListener(this);
		
		name = (TextView) findViewById(R.id.newName);
		task = (TextView) findViewById(R.id.newTask);
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.addNewMission:
			Cursor c1 = dbAdapter.getCursor(dbAdapter.property);
			int dt = (myYear * 364 + myMonth * 30 + myDay) - currentDate;				// минус сегодн€шн€€ дата
			nameP = name.getText().toString();
			taskP = task.getText().toString();
			dateP = myYear + "." + myMonth + "." + myDay;
			timeP = myHour + "." +  myMinute;
			  
			Map<String,String> mp = new LinkedHashMap<String,String>();
			mp.put("name", nameP);
			mp.put("task", taskP);
			mp.put("date", dateP);
			mp.put("time", timeP);
			mp.put("type", "0");
			
			Intent intent = new Intent(this,ShortActivity.class);
			int id = 0;
			String style = null;
			if (c1.moveToFirst()) {
				if(dt <= Integer.valueOf(c1.getString(c1.getColumnIndex("shortTime")))){
					dbAdapter.insert(mp,dbAdapter.tableNameS);
					intent.putExtra("style", ShortActivity.SHORT);
					id = dbAdapter.getCount(dbAdapter.tableNameS);
					style = ShortActivity.SHORT;
				}
				else if(dt < Integer.valueOf(c1.getString(c1.getColumnIndex("mediumTime")))){
					 dbAdapter.insert(mp,dbAdapter.tableNameM);
					 intent.putExtra("style", ShortActivity.MEDIUM);
					 id = dbAdapter.getCount(dbAdapter.tableNameM);
					 style = ShortActivity.MEDIUM;
				}
				else if(dt < Integer.valueOf(c1.getString(c1.getColumnIndex("longTime")))){
					dbAdapter.insert(mp,dbAdapter.tableNameL);
					intent.putExtra("style", ShortActivity.LONG);
					id = dbAdapter.getCount(dbAdapter.tableNameL);
					style = ShortActivity.LONG;
				}
			}
			c1.close();
			id++;
			
			c.set(myYear, myMonth, myDay, myHour-GMT, myMinute);
			long finish = c.getTimeInMillis();
			long w8 = finish - now ;
			Log.d("gow8",String.valueOf(w8));
			Intent intentMyIntentService = new Intent(this, MService.class);
			startService(intentMyIntentService.putExtra("w8", String.valueOf(w8)).putExtra("name", nameP).putExtra("task", taskP)
					.putExtra("date", dateP).putExtra("time", timeP).putExtra("id", String.valueOf(id)).putExtra("style",style));
			 Log.d("Start0","st");
			startActivity(intent);
			 Log.d("Start11","st");
			Log.d("t", "3");
		    finish();
		    break;
		case R.id.dateMission:
			 showDialog(DIALOG_DATE);
			break;
		case R.id.timeMission:
			 showDialog(DIALOG_TIME);
			break;
		}
		
	}
	
	 protected Dialog onCreateDialog(int id) {
	      if (id == DIALOG_DATE) {
	        DatePickerDialog tpd = new DatePickerDialog(this, myCallBackDate, myYear, myMonth, myDay);
	        return tpd;
	      }
	      else
	    	  if (id == DIALOG_TIME) {
	    		  TimePickerDialog tpd = new TimePickerDialog(this, myCallBackTime, myHour, myMinute, true);
	    		  return tpd;
	  	      }
	      return super.onCreateDialog(id);
	    }
	 
	 	OnTimeSetListener myCallBackTime = new OnTimeSetListener() {
		    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		      myHour = hourOfDay;
		      myMinute = minute; 
		     Log.d("time","Time is " + myHour + " hours " + myMinute + " minutes");
		    }
		  };
		  
	    OnDateSetListener myCallBackDate = new OnDateSetListener() {

	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	        int dayOfMonth) {
	      myYear = year;
	      myMonth = monthOfYear;
	      myDay = dayOfMonth;
	      Log.d("date","Date is " + myDay + "/" + myMonth + "/" + myYear);
	    }
	    };
}
