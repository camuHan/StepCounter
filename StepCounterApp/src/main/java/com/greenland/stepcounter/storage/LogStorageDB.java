package com.greenland.stepcounter.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.greenland.stepcounter.data.StepLogCalendar;
import com.greenland.stepcounter.data.StepLogMessage;
import com.greenland.stepcounter.db.DBHandler;

import java.util.ArrayList;
import java.util.Calendar;

public class LogStorageDB extends LogStorage{
	
	final static String mClsName = "LogStorageDB";
	
	Context mCon;
	DBHandler mDbHandler;
	
	public LogStorageDB(Context con){
		super("DB");
		mCon = con;
	}
	
	public ArrayList<StepLogMessage> loadLogList(){
		ArrayList<StepLogMessage> rsvMsgList = new ArrayList<StepLogMessage>();
		try {
			mDbHandler = DBHandler.db_open(mCon);
			String sql = "select * from " + LogStorageDBInfo.STEP_LOG_TBLNAME;
			Cursor cur = mDbHandler.selectAll(sql);
			
			while(cur.moveToNext()){
				long curDate = Calendar.getInstance().getTimeInMillis();;
				String rsvDate = cur.getString(LogStorageDBInfo.eSTEP_LOG_DATE);
//				if(curDate > rsvDate){
//					removeLog((int)cur.getLong(LogStorageDBInfo.eSTEP_LOG_ID));
//					continue;
//				}
				
				StepLogMessage msg = new StepLogMessage();
				msg.setLogId(cur.getLong(LogStorageDBInfo.eSTEP_LOG_ID));
				msg.setDistance(cur.getInt(LogStorageDBInfo.eSTEP_LOG_PHONENUMBER));
				msg.setCal(new StepLogCalendar(cur.getString(LogStorageDBInfo.eSTEP_LOG_DATE)));
				msg.setCount(cur.getInt(LogStorageDBInfo.eSTEP_LOG_TITLE));
				msg.setMsg(cur.getString(LogStorageDBInfo.eSTEP_LOG_MESSAGE));
				rsvMsgList.add(msg);
			}
			cur.close();
		}catch(Exception e) {
			System.out.println(e);
		}finally{
			if(mDbHandler != null)
				mDbHandler.db_close();
		}
		return rsvMsgList;
	}
	
	public long saveLog(StepLogMessage newMsg){
		long result = -1;
		ContentValues values = new ContentValues();
		values.put(LogStorageDBInfo.STEP_LOG_DISTANCE, newMsg.getDistance());
		values.put(LogStorageDBInfo.STEP_LOG_DATE, newMsg.getCal().getDbDate());
		values.put(LogStorageDBInfo.STEP_LOG_COUNT, newMsg.getCount());
		values.put(LogStorageDBInfo.STEP_LOG_MESSAGE, newMsg.getMsg());
		try {
			mDbHandler = DBHandler.db_open(mCon);
			result = mDbHandler.insert(values);
		}catch (Exception e) {
			System.out.println(e);
		}finally{
			if(mDbHandler != null)
				mDbHandler.db_close();
		}
		
		return result;
	}

	public long updateLog(StepLogMessage newMsg, String fieldKey){
		// ex) String fieldKey = "_id="+Integer.toString(id);
		long result = -1;
		ContentValues values = new ContentValues();
		values.put(LogStorageDBInfo.STEP_LOG_DISTANCE, newMsg.getDistance());
		values.put(LogStorageDBInfo.STEP_LOG_DATE, newMsg.getCal().getDbDate());
		values.put(LogStorageDBInfo.STEP_LOG_COUNT, newMsg.getCount());
		values.put(LogStorageDBInfo.STEP_LOG_MESSAGE, newMsg.getMsg());
		try {
			mDbHandler = DBHandler.db_open(mCon);
			result = mDbHandler.update(values, fieldKey);
		}catch (Exception e) {
			System.out.println(e);
		}finally{
			if(mDbHandler != null)
				mDbHandler.db_close();
		}

		return result;
	}

    @Override
    public long updateLog(StepLogMessage newMsg, String fieldKey, String[] key) {
        // ex) String fieldKey = "_id="+Integer.toString(id);
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(LogStorageDBInfo.STEP_LOG_DISTANCE, newMsg.getDistance());
        values.put(LogStorageDBInfo.STEP_LOG_DATE, newMsg.getCal().getDbDate());
        values.put(LogStorageDBInfo.STEP_LOG_COUNT, newMsg.getCount());
        values.put(LogStorageDBInfo.STEP_LOG_MESSAGE, newMsg.getMsg());
        try {
            mDbHandler = DBHandler.db_open(mCon);
            result = mDbHandler.update(values, fieldKey, key);
        }catch (Exception e) {
            System.out.println(e);
        }finally{
            if(mDbHandler != null)
                mDbHandler.db_close();
        }

        return result;
    }

    @Override
    public StepLogMessage loadLog(int key) {
        return null;
    }

    //NO WORK
	public StepLogMessage loadLog(String fieldKey){
		// ex) String sql = "select * from tblName where name='"+name+"'";
		StepLogMessage msg = new StepLogMessage();
		ArrayList<StepLogMessage> rsvMsgList = new ArrayList<StepLogMessage>();
		try {
			mDbHandler = DBHandler.db_open(mCon);
			String sql = "select * from " + LogStorageDBInfo.STEP_LOG_TBLNAME + " where " + fieldKey;
			Cursor cur = mDbHandler.selectKey(sql);

			if(cur.moveToNext() == true){
				msg.setLogId(cur.getLong(LogStorageDBInfo.eSTEP_LOG_ID));
				msg.setDistance(cur.getInt(LogStorageDBInfo.eSTEP_LOG_PHONENUMBER));
				msg.setCal(new StepLogCalendar(cur.getString(LogStorageDBInfo.eSTEP_LOG_DATE)));
				msg.setCount(cur.getInt(LogStorageDBInfo.eSTEP_LOG_TITLE));
				msg.setMsg(cur.getString(LogStorageDBInfo.eSTEP_LOG_MESSAGE));
			}else{
                msg = null;
            }
			cur.close();
		}catch(Exception e) {
			System.out.println(e);
		}finally{
			if(mDbHandler != null)
				mDbHandler.db_close();
		}
		return msg;
	}
	
	public long removeLog(int key){
		String fieldKey = "_id="+Integer.toString(key);
		long result = -1;

		try {
			mDbHandler = DBHandler.db_open(mCon);
			result = mDbHandler.delete(fieldKey);
		}catch (Exception e) {
			System.out.println(e);
		}finally{
			if(mDbHandler != null)
				mDbHandler.db_close();
		}
		return result;
	}
	
	//NO WORK
	public ArrayList<StepLogMessage> getLogList(){
		return null;
	}
	
	//NO WORK
	public int getLogListSize(){
		return 0;
	}
	
	public long deleteAll(){
		long result = -1;
		try {
			mDbHandler = DBHandler.db_open(mCon);
			result = mDbHandler.delete(null);
		}catch (Exception e) {
			
		}finally{
			if(mDbHandler != null)
				mDbHandler.db_close();
		}
		return result;
	}

}
