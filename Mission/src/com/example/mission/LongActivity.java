
package com.example.mission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LongActivity  extends Activity implements OnClickListener{
	
	private DBHelperL dbHelper;
	private SQLiteDatabase db;
	private String LOG_TAG ="test";
	private ContentValues cv;
	private   ListView lvSimple;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listl);
		context = this;
		Button add = (Button) this.findViewById(R.id.addShortL);
		add.setOnClickListener(this);
		
		cv = new ContentValues();
		dbHelper = new DBHelperL(this);
	    // ������������ � ����
	    db = dbHelper.getWritableDatabase();
	    
	   //cv.put("name", "Vasya");
	   // cv.put("email", "email");
	    // ��������� ������ � �������� �� ID
	    //long rowID = db.insert("mytable", null, cv);
	    lvSimple = ( ListView ) findViewById(R.id.lvSimplel);
		lvSimple.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		              int position, long id) {
		    	  Cursor c = db.query("newtableL", null, null, null, null, null, null);
		            Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
		                + id );
		            if(c.moveToPosition((int) id)){
		            	int idColIndex = c.getColumnIndex("id");
		    	        int nameColIndex = c.getColumnIndex("name");
		    	        int taskColIndex = c.getColumnIndex("task");
		    	        Log.d("trast", c.getString(nameColIndex) + " task = " + c.getString(taskColIndex));
		    	        
		    	        Intent intent = new Intent(context, ItemActivity.class);
		    	        intent.putExtra("id",c.getString(idColIndex));
		    	        intent.putExtra("name",c.getString(nameColIndex));
		    	        intent.putExtra("task",c.getString(taskColIndex));
		    			startActivityForResult(intent, 2);
		    		 }
		                   
		            c.close();
		          }

		});

	    registerForContextMenu(lvSimple);
	    showList();
	}
	
	public void showList(){
		  
		  final String ATTRIBUTE_NAME_VALUE = "value";
		  final String ATTRIBUTE_NAME_IMAGE = "image";
		  Log.d(LOG_TAG, "1e");
		  Cursor c = db.query("newtableL", null, null, null, null, null, null);
		  Log.d(LOG_TAG, "2e");
		   int img = R.drawable.ic_long;
		  ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			    Map<String, Object> m;
			    Log.d(LOG_TAG, "3e");
	      // ������ ������� ������� �� ������ ������ �������
	      // ���� � ������� ��� �����, �������� false
	      if (c.moveToFirst()) {

	        // ���������� ������ �������� �� ����� � �������
	        int idColIndex = c.getColumnIndex("id");
	        int nameColIndex = c.getColumnIndex("name");
	        	
	        do {
	        	 m = new HashMap<String, Object>();
	             m.put(ATTRIBUTE_NAME_VALUE, c.getString(nameColIndex) );
	             m.put(ATTRIBUTE_NAME_IMAGE, img);
	            
	             data.add(m);
	          // �������� �������� �� ������� �������� � ����� ��� � ���
	         /* Log.d(LOG_TAG,
	              "ID = " + c.getInt(idColIndex) + 
	              ", name = " + c.getString(nameColIndex) + 
	              ", email = " + c.getString(emailColIndex));*/
	          // ������� �� ��������� ������ 
	          // � ���� ��������� ��� (������� - ���������), �� false - ������� �� �����
	         } while (c.moveToNext());
	        // ������ ���� ���������, �� ������� ����� �������� ������
	        String[] from = {ATTRIBUTE_NAME_VALUE,
	            ATTRIBUTE_NAME_IMAGE };
	        // ������ ID View-�����������, � ������� ����� ��������� ������
	        int[] to = { R.id.tvText, R.id.ivImg };
	        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item,
	                from, to);
	         lvSimple.setAdapter(sAdapter);
	       } else
	        Log.d(LOG_TAG, "0 rows");
	      c.close();
	      
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.addShortL){
			Intent intent = new Intent(this, MissionActivity.class);
			startActivityForResult(intent, 1);
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (data == null) {return;}
	  if(requestCode == 1){
		  String name = data.getStringExtra("name");
		  String task = data.getStringExtra("task");
		  
		  cv.put("name", name);
		  cv.put("task", task);
		  Log.d(LOG_TAG, "rr");
		  
		  long rowID = db.insert("newtableL", null, cv);
		 // lvSimple.removeAllViews();
		  Log.d(LOG_TAG, "1tyr");
		  showList();
	  }
	  if(requestCode == 2){
		  Cursor c = db.query("newtableL", null, null, null, null, null, null);
		  String id = data.getStringExtra("id");
		  Log.d(LOG_TAG,"id = " + id );
		  boolean isDeleted = (db.delete("newtableL", "id" + "=?",
					new String[] { id })) > 0;
		  c.close();
		  showList();
	  }
		  
		  
	  //Log.d(LOG_TAG,"name = " + name );
	}
	
	 @Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
	      ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, 1, 0, "������� ������");
	  }

	  @Override
	  public boolean onContextItemSelected(MenuItem item) {
	    Log.d("ttt", String.valueOf(item.getItemId()));
	    return super.onContextItemSelected(item);
	  }
	  
	  @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		  dbHelper.close();
		super.onStop();
	}
}

class DBHelperL extends SQLiteOpenHelper {

    public DBHelperL(Context context) {
      // ����������� �����������
      super(context, "newDBL", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
     db.execSQL("create table newtableL ("
          + "id integer primary key autoincrement," + "name text,"
          + "task text" + ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
  }
