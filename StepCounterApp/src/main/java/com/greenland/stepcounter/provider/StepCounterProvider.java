package com.greenland.stepcounter.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.greenland.stepcounter.db.DBHandler;

public class StepCounterProvider extends ContentProvider{
	
	final static String mClsName = "StepCounterProvider";
	
	private DBHandler mDbHandler;
	
	public static final Uri CONTENT_URI=
			Uri.parse("content://com.HSH.StepCounterProvider");
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		
		mDbHandler = DBHandler.db_open(getContext());
		
		if(mDbHandler.mDb != null)
			return true;
		else
			return false;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Cursor c = mDbHandler.query(null, null, null, null);
		return c;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub

		long result = mDbHandler.delete(selection, selectionArgs);
		
		return (int)result;
	}
	
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
