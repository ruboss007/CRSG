package com.example.mission;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBMission extends SQLiteOpenHelper {
	
	private String dbName;
	private String tableNameS;
	private String tableNameM;
	private String tableNameL;
	private String tableNameR;
	private String property;
	
    public DBMission(Context context, String _dbName, String _tableNameS, String _tableNameM, String _tableNameL, String _tableNameR, String _tableProperty) {
      super(context, _dbName, null, 1);
      dbName = _dbName;
      tableNameS = _tableNameS;
      tableNameM = _tableNameM;
      tableNameL = _tableNameL;
      tableNameR = _tableNameR;
      property = _tableProperty;
    }

    public void onCreate(SQLiteDatabase db) {
    	db.execSQL("create table " + tableNameS + " ("
          + "id integer primary key autoincrement," + "name text,"
          + "task text," + "date text," + "time text," + "type text" + ");");
    	db.execSQL("create table " + tableNameM + " ("
    	          + "id integer primary key autoincrement," + "name text,"
    	          + "task text," + "date text," + "time text," + "type text" + ");");
    	db.execSQL("create table " + tableNameL + " ("
    	          + "id integer primary key autoincrement," + "name text,"
    	          + "task text," + "date text," + "time text," + "type text" + ");");
    	db.execSQL("create table " + tableNameR + " ("
  	          + "id integer primary key autoincrement," + "name text,"
  	          + "task text," + "date text," + "time text," + "type text" + ");");
    	db.execSQL("create table " + property + " ("
    	          + "id integer primary key autoincrement," + "shortTime text,"
    	          + "mediumTime text," + "longTime text" + ");");
    	
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
  }

