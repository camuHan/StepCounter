package com.greenland.stepcounter.storage;

public class LogStorageDBInfo {
	
	public static final int 
	eSTEP_LOG_ID = 0,
	eSTEP_LOG_PHONENUMBER = 1,
	eSTEP_LOG_DATE = 2,
	eSTEP_LOG_TITLE = 3,
	eSTEP_LOG_MESSAGE = 4;
	
	public static final String STEP_LOG_DBNAME = "steplogdb.db";
	public static final int STEP_LOG_DBVERSION = 1;
	
	// fields
	public static final String STEP_LOG_ID = "_id";				// 0  -  DB Index
	public static final String STEP_LOG_DISTANCE = "distance";	// 1
	public static final String STEP_LOG_DATE = "date";			// 2
	public static final String STEP_LOG_COUNT = "count";			// 3
	public static final String STEP_LOG_MESSAGE = "message";		// 4
	
	public static final String STEP_LOG_TBLNAME = "steplog";
	
	public static final String DATABASE_CREATE_RSVMSG_TBL = "create table "
			+STEP_LOG_TBLNAME
			+"("
			+STEP_LOG_ID+" integer primary key autoincrement, "
			+STEP_LOG_DISTANCE+" text, "
			+STEP_LOG_DATE+" long, "			
			+STEP_LOG_COUNT+" text, "
			+STEP_LOG_MESSAGE+" text"
			+");";
}
