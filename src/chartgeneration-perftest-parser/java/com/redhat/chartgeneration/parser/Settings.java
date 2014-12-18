package com.redhat.chartgeneration.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.redhat.chartgeneration.config.AppData;

public class Settings {
	private static Settings instance = new Settings();
	private Date startTime;
	private Date endTime;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y-M-d h:m:s");
	
	private Settings() {
		Logger logger = AppData.getInstance().getLogger();
		String startTimeProp = System.getProperty("startTime");
		if (startTimeProp != null && !startTimeProp.isEmpty()){
			try {
				startTime = simpleDateFormat.parse(startTimeProp);
				logger.info("specify start time: " + startTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String endTimeProp = System.getProperty("endTime");
		if (endTimeProp != null && !endTimeProp.isEmpty()){
			try {
				endTime = simpleDateFormat.parse(endTimeProp);
				logger.info("specify end time: " + endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public static Settings getInstance() {
		return instance;
	}
	
}
