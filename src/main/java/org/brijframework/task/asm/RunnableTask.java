package org.brijframework.task.asm;

import java.lang.reflect.Method;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import org.brijframework.task.meta.TaskMeta;
import org.brijframework.util.accessor.LogicAccessorUtil;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.LogicUnit;
import org.brijframework.util.reflect.MethodUtil;
import org.brijframework.util.validator.ValidationUtil;

public class RunnableTask implements Runnable {
	public volatile Future<?> taskFuture = null;
	public volatile ScheduledFuture<?> taskScheduledFuture = null;
	public volatile TaskMeta testSetup;
	public RunnableTask(TaskMeta testSetup) {
		this.testSetup=testSetup;
	}
	
	public TaskMeta getTask() {
		return this.testSetup;
	}
	
	@Override
	public void run() {
		this.excuteTesk(this.testSetup);
	}
	
	public void excuteTesk(TaskMeta testSetup) {
		Object instance = null;
		if (ValidationUtil.isValidObject(testSetup.initObject)) {
			Method method = MethodUtil.getMethod(testSetup.getOwner().getTarget(), testSetup.initObject);
			instance = LogicUnit.callMethod(method);
		}else {
			instance = InstanceUtil.getInstance(testSetup.getOwner().getTarget());
		}
		Method collMethod = (Method) testSetup.getTarget();
		Object paramObjects[] = testSetup.getParametors();
		LogicAccessorUtil.callLogic(instance, collMethod, paramObjects);
	}
}
