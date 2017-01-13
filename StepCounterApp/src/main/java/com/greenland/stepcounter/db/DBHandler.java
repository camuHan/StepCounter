package com.greenland.stepcounter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {
	
	final static String mClsName = "DBHandler";
	
	//Context mcon;
	DBHelper mDbHelper;
	public SQLiteDatabase mDb;

	public DBHandler(Context con){
		mDbHelper = new DBHelper(con);
		mDb = mDbHelper.getWritableDatabase();
	}
	
	//DB open
	public static DBHandler db_open(Context con) throws SQLException{
		DBHandler dbHandler = new DBHandler(con);
		return dbHandler;
	}
	//DB close
	public void db_close(){
		mDbHelper.close();
	}
	
	//insert, delete, update, select
	
	public long insert(ContentValues values){
		// ex) ContentValues values = new ContentValues();
		// ex) values.put("_id", id);
		// ex) values.put("name", name);
		long result = mDb.insert(mDbHelper.mDbTableName, null, values);
		return result;
	}
	
	public long update(ContentValues values, String fieldKey){
		// ex) String fieldKey = "_id="+Integer.toString(id);
		long result = mDb.update(mDbHelper.mDbTableName, values, fieldKey, null);
		return result;
	}
	
	public long update(ContentValues values, String field, String[] key){
		// ex) String field = "_id=?";
		// ex) String[] key = new String[]{Integer.toString(id)};
		long result = mDb.update(mDbHelper.mDbTableName, values, field, key);
		return result;
	}

	public void update(String sql){
		mDb.execSQL(sql);
	}

	/*
	public void delete(String sql){
		// ex) String sql = "delete from tblName where _id="+id";
		mDb.execSQL(sql);
	}
	*/
	public long delete(String fieldKey){
		// ex) String fieldKey = "_id="+Integer.toString(id);
		long result = mDb.delete(mDbHelper.mDbTableName, fieldKey, null);
//		Log.i("DB", "delete() - result : " + result + "fieldKey : " + fieldKey);
		return result;
	}
	
	public long delete(String field, String[] key){
		// ex) String field = "_id=?";
		// ex) String[] key = new String[]{Integer.toString(id)};
		long result = mDb.delete(mDbHelper.mDbTableName, field, key);
		return result;
	}
	
	public Cursor selectAll(String sql){
		// ex) String sql = "select * from tblName";
		Cursor cur = mDb.rawQuery(sql, null);
		return cur;
	}
	
	public Cursor selectKey(String sql, String[] key){
		// ex) String sql = "select * from tblName where name like ?";
		// ex) String[] key = new String[]{name};
		Cursor cur = mDb.rawQuery(sql, key);
		return cur;
	}
	
	public Cursor selectKey(String sql){
		// ex) String sql = "select * from tblName where name='"+name+"'";
		Cursor cur = mDb.rawQuery(sql, null);
		return cur;
	}
	
	public Cursor selectKey(String[] fields, String where){
		// ex) db.query(tblName, new String[]{"_id","name"},"_id="+id,null,null,null,null);
		Cursor cur = mDb.query(mDbHelper.mDbTableName, fields, where, null, null, null, null);
		return cur;
	}
	
	public Cursor query(String[] fields, String where, String[] selectionArgs, String sortOrder){
		Cursor cur = mDb.query(mDbHelper.mDbTableName, fields, where, selectionArgs, null, null, sortOrder);
		return cur;
	}
}














