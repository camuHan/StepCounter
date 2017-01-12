package com.greenland.stepcounter;

import android.content.Context;
import android.widget.Toast;

import com.greenland.stepcounter.data.StepLogCalendar;
import com.greenland.stepcounter.data.StepLogMessage;
import com.greenland.stepcounter.storage.LogStorage;
import com.greenland.stepcounter.storage.LogStorageDB;

import java.util.ArrayList;

public final class StepLogManager {
	
	final static String mClsName = "RsvManager";
	final static String mTag = "Life";
	
	private static StepLogManager mStepLogManager;
	Context mCon;

	private StepLogCalendar mCal;
	private boolean bMusicService;
	
	LogStorage mStorage;
	ArrayList<StepLogMessage> mStepLogList;
	
	public StepLogCalendar getCal() {
		return mCal;
	}
	public void setCal(StepLogCalendar cal) {
		mCal = cal;
	}
	
	private StepLogManager(Context con){
		mCon = con;
//		if(false){
//			mStorage = new MsgStorageFile(mCon);
//		}else{
			mStorage = new LogStorageDB(mCon);
//		}
		
		mStepLogList = mStorage.loadLogList();
		bMusicService = false;
		
	}
	
	public static synchronized StepLogManager getInstance(){
//		if(mStepLogManager == null){
//			return null;
//		}
		return mStepLogManager;
	}
	public static synchronized StepLogManager getInstance(Context con){
		if(mStepLogManager == null){
			mStepLogManager = new StepLogManager(con);
		}
		return mStepLogManager;
	}
	
	void setNowDate(){
		mCal = new StepLogCalendar();
	}
	
	void setCalendar(){
		mCal = new StepLogCalendar();
		//setNowDate();
	}
	
	void setMusicService(boolean check){
		bMusicService = check;
	}
	boolean getMusicService(){
		return bMusicService;
	}
	
	void setDate(int year, int month, int day){
		mCal.setYear(year);
		mCal.setMonth(month);
		mCal.setDay(day);
	}
	void setTime(int hour, int min){
		mCal.setHour(hour);
		mCal.setMin(min);
	}
	
	void saveMessage(String title, String msg, String pnum){
		StepLogMessage newLog = new StepLogMessage();

		newLog.setCal(mCal);
		newLog.setCount(title);
		newLog.setMsg(msg);
		newLog.setDistance(pnum);
		
		long result = mStorage.saveLog(newLog);
		if(result == -1){	
			Toast.makeText(mCon, "FAIL", Toast.LENGTH_SHORT).show();
		}else{	
//			newMsg.setLogId(result);
			mStepLogList.add(newLog);
		}
	}
	
	StepLogMessage loadMessage(int key){
		return mStepLogList.get(key);
	}
	
	void removeMessage(int key){
		int msgId = (int) mStepLogList.get(key).getLogId();
		long result = mStorage.removeLog(msgId);
		if(result == -1) {
			Toast.makeText(mCon, "FAIL!", Toast.LENGTH_SHORT).show();
		}else{
			mStepLogList.remove(key);
		}
	}
	
	ArrayList<StepLogMessage> loadMsgList(){
		mStepLogList.clear();
		mStepLogList.addAll(mStorage.loadLogList());

		return mStepLogList;
	}
	
	ArrayList<StepLogMessage> getMsgList(){
		return mStepLogList;
	}
	
	void deleteAll(){
		long result = mStorage.deleteAll();
		
		if(result == -1){
			Toast.makeText(mCon, "FAIL!", Toast.LENGTH_SHORT).show();
		}else{
			mStepLogList.clear();
		}
	}
}
