package org.brijframework.task.meta;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.brijframework.model.info.OwnerModelInfo;
import org.brijframework.model.info.asm.AbstractModelInfo;

public class TaskInfo extends AbstractModelInfo<Method>{
	
	private long initial=1;
	private long interval=1;
	private long waiting=1;
	private Method target;
	private Object[] parametors;
	private String initObject;
	
	public TaskInfo(OwnerModelInfo owner,Method target) {
		this.target=target;
		this.setOwner(owner);
	}
	
	public void setInitObject(String initObject) {
		this.initObject = initObject;
	}
	
	public String getInitObject() {
		return initObject;
	}
	
	public long initial() {
		return initial;
	}
	
	public void setInitial(long initial) {
		this.initial = initial;
	}
	
	public long waiting() {
		return waiting;
	}

	public void setWaiting(long waiting) {
		this.waiting = waiting;
	}
	
	public long interval() {
		return interval;
	}
	
	public void setInterval(long interval) {
		this.interval = interval;
	}
	
	public TimeUnit unit() {
		return TimeUnit.MINUTES;
	}
	
	@Override
	public Method getTarget() {
		return target;
	}
	
	public void setTarget(Method target) {
		this.target = target;
	}

	public Object[] getParametors() {
		return parametors;
	}
	
}

