package org.brijframework.task.meta;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.brijframework.meta.KeyInfo;
import org.brijframework.meta.asm.AbstractMetaInfo;
import org.brijframework.util.casting.TimeUtil;
import org.brijframework.util.validator.ValidationUtil;

public class TaskMeta extends AbstractMetaInfo{

	public long initialDelay = 0;

	public TimeUnit initialDelayUnit;

	public long period = 1; //loop time

	public TimeUnit periodUnit = TimeUnit.MINUTES;// loop unit

	public String preTask = "";//Method Name

	public String postTask = "";//Method Name

	public String startDate = "";

	public String stopDate = "";

	public String dateFormat = "";

	public String startTime = "";// = "3:00 am",  "3:00AM", "3:00 PM", "3:00pm"

	public String stopTime = ""; // = "3:00 am",  "3:00AM", "3:00 PM", "3:00pm"

	public boolean isExecute = false;

	public String initObject;

	public boolean isReport = false;

	public TaskDetail detail = new TaskDetail();

	@SuppressWarnings({ "deprecation", "static-access" })
	public long getWaitTime() {
		if (ValidationUtil.isValidObject(this.startDate)) {
			this.detail.startDate = TimeUtil.getDateTime(this.startDate, this.dateFormat);
			if (ValidationUtil.isValidObject(this.stopDate)) {
				this.detail.stopDate = TimeUtil.getDateTime(this.stopDate, this.dateFormat);
			}
			this.initialDelay = TimeUtil.unitDifferenceFromDate(this.startDate, TimeUnit.MILLISECONDS);
			this.initialDelayUnit = TimeUnit.MILLISECONDS;
			if (this.initialDelay > -1) {
				this.isExecute = true;
			}
			return this.initialDelay;
		}
		if (ValidationUtil.isValidObject(this.startTime)) {
			this.detail.startDate = TimeUtil.getDateTime(this.startTime);
			if ((this.detail.startDate.getTime() - new Date().getTime()) < 0) {
				this.detail.startDate.setDate(this.detail.startDate.getDate() + 1);
			}
			if (ValidationUtil.isValidObject(this.stopTime)) {
				this.detail.stopDate = TimeUtil.getDateTime(this.stopTime);
				if (((this.detail.stopDate.getTime() - new Date().getTime()) < 0) || ((this.detail.stopDate.getTime() - this.detail.startDate.getTime()) < 0)) {
					this.detail.stopDate.setDate(this.detail.stopDate.getDate() + 1);
				}
			}
			this.initialDelay = TimeUtil.unitDifferenceFromTime(this.startTime, TimeUnit.MILLISECONDS);
			this.initialDelayUnit = TimeUnit.MILLISECONDS;
			if (this.initialDelay > -1) {
				this.isExecute = true;
			}
			return this.initialDelay;
		}
		if (this.initialDelayUnit.equals(this.initialDelayUnit.NANOSECONDS)) {
			this.initialDelay = TimeUtil.miliseconds(this.initialDelay, this.periodUnit);
			if (this.initialDelay > -1) {
				this.isExecute = true;
			}
			return this.initialDelay;
		}
		this.initialDelay = TimeUtil.miliseconds(this.initialDelay, this.initialDelayUnit);
		if (this.initialDelay > -1) {
			this.isExecute = true;
		}
		return this.initialDelay;
	}

	public void displayReport() {
		System.out.print("=================================================================");
		System.out.format("\nTask ID               %50s", this.getId());
		System.out.format("\nWait                 %50s", this.initialDelay + "  " + this.initialDelayUnit);
		System.out.format("\nRemaining            %50s", TimeUtil.minutes(this.initialDelay, this.initialDelayUnit) + "  minutes");
		System.out.format("\nPeriod               %50s", this.period + " " + this.periodUnit);
		System.out.format("\nStarDateTime         %50s", this.detail.startDate);
		System.out.format("\nStopDateTime         %50s", this.detail.stopDate);
		System.out.println("\n=================================================================");
	}

	private Method target;
	
	private Class<?> owner;
	
	@Override
	public Method getTarget() {
		return target;
	}
	
	public void setTarget(Method target) {
		this.target = target;
	}
	
	public Class<?> getOwner() {
		return owner;
	}
	
	public void setOwner(Class<?> owner) {
		this.owner = owner;
	}

	@Override
	public KeyInfo getKeyInfo() {
		return null;
	}

	public Object[] getParametors() {
		return null;
	}

	public void init(Class<?> clazz, Method method) {
		// TODO Auto-generated method stub
		
	}

}
