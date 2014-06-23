package com.example.mission;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity implements OnClickListener {
	
	private EditText tfShort;
	private EditText tfMedium;
	private EditText tfLong;
	private Button saveBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		Log.d("checkSet","ye1");
		tfShort = (EditText) findViewById(R.id.editShort);
		tfMedium = (EditText) findViewById(R.id.editMedium);
		tfLong = (EditText) findViewById(R.id.editLong);
		saveBtn = (Button) findViewById(R.id.saveProperty);
		saveBtn.setOnClickListener(this);
		Cursor c = dbAdapter.getCursor(dbAdapter.property);
		
		if (c.moveToFirst()) {
			tfShort.setText(c.getString(c.getColumnIndex("shortTime")));
			tfMedium.setText(c.getString(c.getColumnIndex("mediumTime")));
			tfLong.setText(c.getString(c.getColumnIndex("longTime")));
		}
		c.close();
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.saveProperty){
			Map <String,String> m = new HashMap<String,String>();
			m.put("shortTime",tfShort.getText().toString());
			m.put("mediumTime",tfMedium.getText().toString());
			m.put("longTime",tfLong.getText().toString());
			dbAdapter.update("1", dbAdapter.property, m);
			Intent intent = new Intent(this,MenuActivity.class);
			startActivity(intent);
		}
	}
}
