package org.brijframework.task.jobs;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledFuture;

import org.brijframework.lifecycle.Initializer;
import org.brijframework.task.meta.ScheduledTaskMeta;
import org.brijframework.util.accessor.LogicAccessorUtil;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.LogicUnit;
import org.brijframework.util.reflect.MethodUtil;
import org.brijframework.util.validator.ValidationUtil;

public class ScheduledTaskRunner implements Runnable, Initializer {

	public Object instance; 
	public ScheduledTaskMeta task;
	public ScheduledFuture<?> future;
	
	public ScheduledTaskRunner(ScheduledTaskMeta task) {
		this.task=task;
		this.init();
	}
	
	public ScheduledTaskMeta getTask() {
		return task;
	}
	
	@Override
	public void init() {
		if (ValidationUtil.isValidObject(getTask().getInitObject())) {
			Method method = MethodUtil.getMethod(getTask().getOwner().getTarget(), getTask().getInitObject());
			this.instance = LogicUnit.callMethod(method);
		}else {
			this.instance = InstanceUtil.getInstance(getTask().getOwner().getTarget());
		}
	}
	
	@Override
	public void run() {
		excuteTesk();
	}
	
	public void excuteTesk() {
		Method collMethod = (Method) getTask().getTarget();
		Object paramObjects[] = getTask().getParametors();
		LogicAccessorUtil.callLogic(this.instance, collMethod, paramObjects);
	}
}
