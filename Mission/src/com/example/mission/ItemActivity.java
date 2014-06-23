package com.example.mission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ItemActivity extends Activity implements OnClickListener{
	private  TextView tv;
	private  TextView tv2;
	private  String id;
	private String style;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.missionitem);
		
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");   
	    String task = intent.getStringExtra("task");
	    String date = intent.getStringExtra("date");
	    String time = intent.getStringExtra("time");
	    style = intent.getStringExtra("style");
	    id = intent.getStringExtra("id");
	    Log.d("ID ", id);
	    Log.d("style", style);
	    tv = (TextView) findViewById(R.id.itemDesc);
	    tv2 = (TextView) findViewById(R.id.descItem);
	    TextView tv3 = (TextView) findViewById(R.id.dateShow);
	    TextView tv4 = (TextView) findViewById(R.id.timeShow);
	    Button btn = (Button) findViewById(R.id.buttDesc);
	    btn.setOnClickListener(this);
	    tv.setText(name);
	    tv2.setText(task);
	    tv3.setText(date);
	    tv4.setText(time);
}

	@Override
	public void onClick(View arg0) {
		Intent  inten = new Intent(this,ShortActivity.class);
	 	switch(style){
		  case ShortActivity.SHORT:
			  dbAdapter.deleteById(id,dbAdapter.tableNameS);
			  inten.putExtra("style", ShortActivity.SHORT);
			  startActivity(inten);
			  break;
		  case ShortActivity.MEDIUM:
			  dbAdapter.deleteById(id,dbAdapter.tableNameM);
			  inten.putExtra("style", ShortActivity.MEDIUM);
			  startActivity(inten);
			 break;
		  case ShortActivity.LONG:
			  dbAdapter.deleteById(id,dbAdapter.tableNameL);
			  inten.putExtra("style", ShortActivity.LONG);
			  startActivity(inten);
			  break;
		  case ShortActivity.READY:
			  dbAdapter.deleteById(id,dbAdapter.tableNameL);
			  inten.putExtra("style", ShortActivity.LONG);
			  startActivity(inten);
			  break;
	    }}
}
