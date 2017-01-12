package com.greenland.stepcounter.data;

import java.io.Serializable;

public class StepLogMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	final static String mClsName = "RsvMessage"; 
	
	
	private StepLogCalendar mRsvCal;
	private long mLogId;
	private int mCount;
	private String mMsg;
	private int mDistance;
	
	public static enum eELEMENT_KEY{
		LOG_LOGID,
		LOG_COUNT,
		LOG_MSG,
		LOG_DISTANCE,
		LOG_CALENDAR,
		
		LOG_NULL,
		LOG_ALL,
		LOG_ETC
	}
	
	public final static String KEY_LOGID = "LogId";
	public final static String KEY_LOGCOUNT = "LogCount";
	public final static String KEY_LOGMESSAGE = "LogMessage";
	public final static String KEY_LOGDISTANCE = "LogDistance";
	public final static String KEY_LOGCALENDAR = "LogCalendar";
	
	
	public StepLogMessage(){
		//LogManager.getInstance().log(LogManager.INFO, mLogTag, "Message Create", mClsName);
	}
	
	public StepLogMessage(StepLogCalendar cal, int count, String msg, int distance){
		mRsvCal = cal;
		mCount = count;
		mMsg = msg;
		mDistance = distance;
	}
	
	/********************* Date *********************/
	public void setCal(StepLogCalendar cal){
		mRsvCal = cal;
	}
	public StepLogCalendar getCal(){
		return mRsvCal;
	}
	/********************* Date *********************/
	
	/********************* MsgId *********************/
	public void setLogId(long logId){
		mLogId = logId;
	}
	public long getLogId(){
		return mLogId;
	}
	/********************* MsgId *********************/
	
	/********************* Title *********************/
	public void setCount(int count){
		mCount = count;
	}
	public int getCount(){
		return mCount;
	}
	/********************* Title *********************/
	
	/********************* Message *********************/
	public void setMsg(String msg){
		mMsg = msg;
	}
	public String getMsg(){
		return mMsg;
	}
	/********************* Message *********************/
	
	/********************* PhoneNumber *********************/
	public void setDistance(int pnum){
		mDistance = pnum;
	}
	public int getDistance(){
		return mDistance;
	}
	/********************* PhoneNumber *********************/
}
