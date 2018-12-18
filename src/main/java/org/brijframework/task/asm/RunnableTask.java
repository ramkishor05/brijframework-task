package org.brijframework.task.asm;

import java.lang.reflect.Method;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import org.brijframework.task.meta.TaskMeta;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.LogicUnit;
import org.brijframework.util.reflect.MethodUtil;
import org.brijframework.util.validator.ValidationUtil;

public class RunnableTask implements Runnable {
	public volatile Future<?> taskFuture = null;
	public volatile ScheduledFuture<?> taskScheduledFuture = null;
	public volatile TaskMeta testSetup;

	@Override
	public void run() {
		synchronized (this.testSetup) {
			this.testSetup.getWaitTime();
			this.excuteTesk(this.testSetup);
		}
	}

	public void preTask(TaskMeta testSetup) {
		if (ValidationUtil.isValidObject(testSetup.preTask)) {
			String[] stup = testSetup.preTask.split("_");
			if (stup.length == 2) {
				String clazzName = stup[0];
				String methodName = stup[1];
				/*String key = (String) ClassContainer.getContainer().searchGroup(clazzName).getId();
				if (ValidationUtil.isValidObject(key)) {
					ClassMeta classMeta = ClassContainer.getContainer().getObject(key, clazzName);
					Class<?> clazz = ClassUtil.getClass(classMeta.classPath);
					Object obj = InstanceUtil.getInstance(clazz);
					testSetup.detail.preTaskResult = LogicUnit.callMethod(obj, methodName);
				}*/
			}
		}
	}

	public void excuteTesk(TaskMeta testSetup) {
		if (!testSetup.isExecute) {
			return;
		}
		this.preTask(testSetup);
		long t1 = System.currentTimeMillis();
		Object instance = null;
		if (ValidationUtil.isValidObject(testSetup.initObject)) {
			Method method = MethodUtil.getMethod(testSetup.getOwner(), testSetup.initObject);
			instance = LogicUnit.callMethod(method);
		}
		instance = ValidationUtil.isValidObject(instance) ? instance : InstanceUtil.getInstance(testSetup.getOwner());
		Method method = (Method) testSetup.getTarget();
		Object objectParam[] = testSetup.getParametors();
		testSetup.detail.taskResult = LogicUnit.callMethod(instance, method.getName(), objectParam);
		testSetup.detail.totalExecutionTime = (((System.currentTimeMillis() - t1) / 100) / 10.0);
		this.postTask(testSetup);
	}

	public void postTask(TaskMeta testSetup) {
		testSetup.detail.lastExecutionTime = testSetup.detail.totalExecutionTime;
		if (ValidationUtil.isValidObject(testSetup.postTask)) {
			String[] stup = testSetup.postTask.split("_");
			if (stup.length == 2) {
				String clazzName = stup[0];
				String methodName = stup[1];
				/*String key =  (String) ClassContainer.getContainer().searchGroup(clazzName).getId();
				if (ValidationUtil.isValidObject(key)) {
					ClassMeta classMeta = ClassContainer.getContainer().getObject(key, clazzName);
					Class<?> clazz = ClassUtil.getClass(classMeta.classPath);
					Object obj = InstanceUtil.getInstance(clazz);
					testSetup.detail.postTaskResult = LogicUnit.callMethod(obj, methodName);
				}*/
			}
		}
		if (testSetup.isReport) {
			testSetup.displayReport();
		}
	}
}
