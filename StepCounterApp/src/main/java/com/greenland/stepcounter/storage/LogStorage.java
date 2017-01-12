package com.greenland.stepcounter.storage;

import com.greenland.stepcounter.data.StepLogMessage;

import java.util.ArrayList;

public abstract class LogStorage {
	
	final static String mClsName = "LogStorage";
	
	LogStorage(String strType){}
	public abstract ArrayList<StepLogMessage> loadLogList();
	public abstract long saveLog(StepLogMessage msg);
	public abstract long updateLog(StepLogMessage msg, String fieldKey);
	public abstract long updateLog(StepLogMessage msg, String fieldKey, String[] key);
	public abstract StepLogMessage loadLog(int key);
	public abstract StepLogMessage loadLog(String fieldKey);
	public abstract long removeLog(int key);
	public ArrayList<StepLogMessage> getLogList() {
		// TODO Auto-generated method stub
		return null;
	}
	public abstract int getLogListSize();
	public abstract long deleteAll();
}
