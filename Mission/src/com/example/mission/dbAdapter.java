package com.example.mission;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class dbAdapter {
	
	private static DBMission dbMission;
	private static SQLiteDatabase db;
	
	private static Cursor cursor;
	private static ContentValues cv;
	private static String dbName;
	public static String tableNameS = "short";
	public static String tableNameM = "medium";
	public static String tableNameL = "long";
	public static String tableNameR = "ready";
	public static String property = "property";
	
	public static void createAdapter(Context context){
		
		dbName = "BDmission";
		dbMission = new DBMission (context, dbName, tableNameS, tableNameM, tableNameL, tableNameR, property);
		cv = new ContentValues();
	    // подключаемся к базе
	    db = dbMission.getWritableDatabase();
	    
	    Map<String,String> m = new HashMap<String,String>();
    	m.put("shortTime", "7");
    	m.put("mediumTime", "20");
    	m.put("longTime", "50");
    	dbAdapter.insert(m, property);
	}
	
	public static Cursor getCursor(String _tableName){
		cursor = db.query(_tableName, null, null, null, null, null, null);
		return cursor;
	}
	
	public static int getCount(String _tableName){
		cursor = db.query(_tableName, null, null, null, null, null, null);
		int cnt = cursor.getCount();
		cursor.close();
		return cnt;
	}
	
	public static Cursor getCursorWhere(String _tableName,int val){
		switch(val){
			case 0:
				String []selectionArgs = new String[] { "1" };
			    cursor = db.query(_tableName, null, "type < ?", selectionArgs, null, null, null);
				break;
			case 1:
				String []selectionArgs2 = new String[] { "0" };
			    cursor = db.query(_tableName, null, "type > ?", selectionArgs2, null, null, null);
				break;
		}
	  return cursor;
	}
	
	public static void setContext(Context c){
		
	}
	
	public static void cursorClose(){
		cursor.close();
	}
	
	public static void dbClose(){
		dbMission.close();
	}
	
	public static boolean deleteById(String id, String _tableName){
		  boolean isDeleted = (db.delete(_tableName, "id" + "=?",
					new String[] { id })) > 0;
		return isDeleted;
	}
	
	public static void update(String id, String _tableName, Map<String, String> m){
		for(Entry<String, String> e : m.entrySet() ){
			cv.put(e.getKey(), e.getValue());
		}
		db.update(_tableName, cv, "id = " + id, null);
		cv.clear();
	}
	
	public static SQLiteDatabase getDB(){
		return db;
	}
	
	public static long insert(Map<String,String> m, String tableName){
		for(Entry<String, String> e : m.entrySet() ){
			cv.put(e.getKey(), e.getValue());
		}
		long id = db.insert(tableName, null, cv);
		cv.clear();
		return id;
	}
}
