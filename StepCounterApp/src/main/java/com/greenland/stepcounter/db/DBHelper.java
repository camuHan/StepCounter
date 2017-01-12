package com.greenland.stepcounter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.greenland.stepcounter.storage.LogStorageDBInfo;

public class DBHelper extends SQLiteOpenHelper{
	
	public String mDbTableName = LogStorageDBInfo.STEP_LOG_TBLNAME;

	public DBHelper(Context context) {
		super(context, LogStorageDBInfo.STEP_LOG_DBNAME, null, LogStorageDBInfo.STEP_LOG_DBVERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(LogStorageDBInfo.DATABASE_CREATE_RSVMSG_TBL);
	
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exist " + mDbTableName);
		onCreate(db);
	}
}
