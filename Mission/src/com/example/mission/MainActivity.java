
package com.example.mission;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {
	private static final long SPLASHTIME = 1800;
	private static final int STOPSPLASH = 0;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo);
		context = this;
		dbAdapter.createAdapter(this);
	  	Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		//app();
	}
	
	private Handler splashHandler = new Handler() { //создаем новый хандлер
		public void handleMessage(Message msg) {
		       switch (msg.what) {
			       case STOPSPLASH:
			    	   Intent intent = new Intent(context, MenuActivity.class);
			    	   startActivity(intent);
			    	   ((Activity) context).finish();
				   	   break;
		       }
		 
		       super.handleMessage(msg);
		   }
		};
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	

}

