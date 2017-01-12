package com.greenland.stepcounter.data;

import java.io.Serializable;
import java.util.Calendar;

public class StepLogCalendar implements Serializable{
	

	private static final long serialVersionUID = 1L;

	private Calendar cal;
	
	public static final int PICK_DATE = 0;
	public static final int PICK_TIME = 1;
	
	public StepLogCalendar(long dbDate){
		cal = Calendar.getInstance();
		cal.setTimeInMillis(dbDate);
	}
	
	public StepLogCalendar(){
		cal = Calendar.getInstance();
	}
	
	public Calendar getCal() {
		return cal;
	}
	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	public int getYear(){
		return cal.get(Calendar.YEAR);
	}
	public void setYear(int year){
		cal.set(Calendar.YEAR, year);
	}
	
	public int getMonth(){
		return cal.get(Calendar.MONTH);
	}
	public void setMonth(int month){
		cal.set(Calendar.MONTH, month);
	}
	
	public int getDay(){
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public void setDay(int day){
		cal.set(Calendar.DAY_OF_MONTH, day);
	}
	
	public int getHour(){
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	public void setHour(int hour){
		cal.set(Calendar.HOUR_OF_DAY, hour);
	}
	
	public int getMin(){
		return cal.get(Calendar.MINUTE);
	}
	public void setMin(int min){
		cal.set(Calendar.MINUTE, min);
	}
	
	public long getDbDate(){
		return cal.getTimeInMillis();
	}
	
}
