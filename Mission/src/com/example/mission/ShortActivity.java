package com.example.mission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShortActivity extends Activity implements OnClickListener{
	
	private String LOG_TAG ="test";
	private ListView lvSimple;
	private Context context;
	public final static String SHORT = "Short";
	public final static String MEDIUM = "Medium";
	public final static String LONG = "Long";
	public final static String READY = "Redy";
	private String style;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		style = intent.getStringExtra("style");
		setContentView(R.layout.list);
		TextView title = (TextView) findViewById(R.id.styleTitle);
		title.setText(style);
				
		context = this;
		Button add = (Button) this.findViewById(R.id.addShort);
		add.setOnClickListener(this);
		
	   //cv.put("name", "Vasya");
	   // cv.put("email", "email");
	    // вставляем запись и получаем ее ID
	    //long rowID = db.insert("mytable", null, cv);
	    lvSimple = ( ListView ) findViewById(R.id.lvSimple);
		lvSimple.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		              int position, long id) {
		    	  Cursor c = null;
				  switch(style){
					  case SHORT:
						  c = dbAdapter.getCursorWhere(dbAdapter.tableNameS,0);
						  break;
					  case MEDIUM:
						  c = dbAdapter.getCursorWhere(dbAdapter.tableNameM,0);
						  break;
					  case LONG:
						  c = dbAdapter.getCursorWhere(dbAdapter.tableNameL,0);
						  break;
					  case READY:
						  c = dbAdapter.getCursorWhere(dbAdapter.tableNameR,1);
						  break;
				  }
		            Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
		                + id );
		            if(c.moveToPosition((int) id)){
		            	int idColIndex = c.getColumnIndex("id");
		    	        int nameColIndex = c.getColumnIndex("name");
		    	        int taskColIndex = c.getColumnIndex("task");
		    	        int dateColIndex = c.getColumnIndex("date");
		    	        int timeColIndex = c.getColumnIndex("time");
		    	        Log.d("trast", c.getString(nameColIndex) + " task = " + c.getString(taskColIndex));
		    	        
		    	        Intent intent = new Intent(context, ItemActivity.class);
		    	        intent.putExtra("id",c.getString(idColIndex));
		    	        intent.putExtra("name",c.getString(nameColIndex));
		    	        intent.putExtra("task",c.getString(taskColIndex));
		    	        intent.putExtra("date",c.getString(dateColIndex));
		    	        intent.putExtra("time",c.getString(timeColIndex));
		    	        intent.putExtra("style",style);
		    	        c.close();
		    			startActivity(intent);
		    		 }
		                   
		            
		          }

		});

	    registerForContextMenu(lvSimple);
	    showList();
	}
	
	public void showList(){
		  lvSimple.removeAllViewsInLayout();
		  final String ATTRIBUTE_NAME_VALUE = "value";
		  final String ATTRIBUTE_NAME_IMAGE = "image";
		  final String ATTRIBUTE_NAME_DATE = "date";
		  final String ATTRIBUTE_NAME_TIME = "time";
		  Log.d(LOG_TAG, "1e");
		  
		  Cursor c = null;
		  int img = 0;
		  switch(style){
			  case SHORT:
				  c = dbAdapter.getCursorWhere(dbAdapter.tableNameS,0);
				  img = R.drawable.ic_short;
				  break;
			  case MEDIUM:
				  c = dbAdapter.getCursorWhere(dbAdapter.tableNameM,0);
				  img = R.drawable.ic_medium;
				  break;
			  case LONG:
				  c = dbAdapter.getCursorWhere(dbAdapter.tableNameL,0);
				  img = R.drawable.ic_long;
				  break;
			  case READY:
				  c = dbAdapter.getCursorWhere(dbAdapter.tableNameR,1);
				  img = R.drawable.ic_launcher;
				  break;
		  }
		 
		  ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			    Map<String, Object> m;
			    Log.d(LOG_TAG, "3e");
	      // ставим позицию курсора на первую строку выборки
	      // если в выборке нет строк, вернется false
	      if (c.moveToFirst()) {

	        // определяем номера столбцов по имени в выборке
	        int idColIndex = c.getColumnIndex("id");
	        int nameColIndex = c.getColumnIndex("name");
	        int dateColIndex = c.getColumnIndex("date");
	        int timeColIndex = c.getColumnIndex("time");
	        do {
	        	 m = new HashMap<String, Object>();
	             m.put(ATTRIBUTE_NAME_VALUE, c.getString(nameColIndex) );
	             m.put(ATTRIBUTE_NAME_DATE, c.getString(dateColIndex) );
	             m.put(ATTRIBUTE_NAME_TIME, c.getString(timeColIndex) );
	             m.put(ATTRIBUTE_NAME_IMAGE, img);
	            
	             data.add(m);
	          // получаем значения по номерам столбцов и пишем все в лог
	         /* Log.d(LOG_TAG,
	              "ID = " + c.getInt(idColIndex) + 
	              ", name = " + c.getString(nameColIndex) + 
	              ", email = " + c.getString(emailColIndex));*/
	          // переход на следующую строку 
	          // а если следующей нет (текущая - последняя), то false - выходим из цикла
	         } while (c.moveToNext());
	        // массив имен атрибутов, из которых будут читаться данные
	        String[] from = {ATTRIBUTE_NAME_VALUE, ATTRIBUTE_NAME_DATE, ATTRIBUTE_NAME_TIME,
	            ATTRIBUTE_NAME_IMAGE };
	        // массив ID View-компонентов, в которые будут вставлять данные
	        int[] to = { R.id.tvText, R.id.tvDate, R.id.tvTime, R.id.ivImg };
	        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item,
	                from, to);
	         lvSimple.setAdapter(sAdapter);
	       } else
	        Log.d(LOG_TAG, "0 rows");
	      c.close();
	      
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.addShort){
			Intent intent = new Intent(this, MissionActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	protected void onDestroy() {
		//dbAdapter.dbClose();
		super.onDestroy();
	}
	 
	
	@Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
	      ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, 1, 0, "Удалить запись");
	  }

	  @Override
	  public boolean onContextItemSelected(MenuItem item) {
	    Log.d("ttt", String.valueOf(item.getItemId()));
	    return super.onContextItemSelected(item);
	  }
}
