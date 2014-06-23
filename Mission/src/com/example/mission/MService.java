package com.example.mission;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

public class MService extends IntentService {

	final String LOG_TAG = "IntentServiceLogs";
	NotificationManager nm;
	private String timeT;
	
	String name;   
    String task;
    String date;
    String time;
    String id;
    String style;
	public MService() {
		super("myname");
	}

	public void onCreate() {
		super.onCreate();
		Log.d(LOG_TAG, "onCreate");
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(LOG_TAG, "onCommand");
	    //sendNotif();
	    return super.onStartCommand(intent, flags, startId);
	  }
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		timeT = intent.getStringExtra("w8");
		
		name = intent.getStringExtra("name");   
	    task = intent.getStringExtra("task");
	    date = intent.getStringExtra("date");
	    time = intent.getStringExtra("time");
	    id = intent.getStringExtra("id");
	    style = intent.getStringExtra("style");
		try {
			TimeUnit.MILLISECONDS.sleep(Long.valueOf(timeT));
			sendNotif();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	void sendNotif() {
		 Log.d(LOG_TAG, "t2");
	    // 1-я часть
	    Notification notif = new Notification(R.drawable.ic_launcher, "Time 2 mission", 
	      System.currentTimeMillis());
	    
	    // 3-я часть
	    Intent intent = new Intent(this, ItemActivity.class);
	    intent.putExtra("name", name);
	    intent.putExtra("task", task);
	    intent.putExtra("date", date);
	    intent.putExtra("time", time);
	    intent.putExtra("style", style);
	    intent.putExtra("id", String.valueOf(id));
	    /*
	     Log.d("style", style);
	    int nID = Integer.valueOf(id);
	    nID--;
	    Log.d("ID2", String.valueOf(nID));
	    switch(style){
		  case ShortActivity.SHORT:
			  Log.d("Op1", "op");
			  dbAdapter.deleteById("9",dbAdapter.tableNameS);
			  Log.d("Op1", "op2");
			  break;
		  case ShortActivity.MEDIUM:
			  dbAdapter.deleteById(String.valueOf(nID),dbAdapter.tableNameM);
			 break;
		  case ShortActivity.LONG:
			  dbAdapter.deleteById(String.valueOf(nID),dbAdapter.tableNameL);
			  break;
	    }
	    Log.d(LOG_TAG, "t4");
	    Map<String,String> mp = new LinkedHashMap<String,String>();
			mp.put("name", name);
			mp.put("task", task);
			mp.put("date", date);
			mp.put("time", time);
			mp.put("type", "1");
		dbAdapter.insert(mp,dbAdapter.tableNameR);*/
	    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
	    
	    // 2-я часть
	    notif.setLatestEventInfo(this, name, task, pIntent);
	    
	    // ставим флаг, чтобы уведомление пропало после нажатия
	    notif.flags |= Notification.FLAG_AUTO_CANCEL;
	    
	    // отправляем
	    nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    nm.notify(1, notif);
	  }
}