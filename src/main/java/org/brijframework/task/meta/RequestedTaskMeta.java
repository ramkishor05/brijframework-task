package org.brijframework.task.meta;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.brijframework.meta.reflect.ClassMeta;

public class RequestedTaskMeta extends TaskInfo{

	public RequestedTaskMeta(ClassMeta owner, Method target) {
		super(owner, target);
	}

	public TimeUnit initialUnit;// loop unit
	
	@Override
	public void init() {
		this.setId(this.getOwner().getTarget().getSimpleName()+"_"+this.getTarget().getName());
	}

}