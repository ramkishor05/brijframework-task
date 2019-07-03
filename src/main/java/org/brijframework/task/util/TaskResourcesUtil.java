package org.brijframework.task.util;

import java.lang.reflect.Method;
import java.util.Map;

import org.brijframework.model.info.OwnerModelInfo;
import org.brijframework.support.monitor.RequestedTask;
import org.brijframework.support.monitor.ScheduledTask;
import org.brijframework.support.monitor.Task;
import org.brijframework.task.meta.RequestedTaskMeta;
import org.brijframework.task.meta.ScheduledTaskMeta;
import org.brijframework.task.meta.TaskMeta;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.AnnotationUtil;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.text.StringUtil;

public class TaskResourcesUtil {
	
	public static RequestedTaskMeta getRequestedTask(OwnerModelInfo owner, Method target) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(target, RequestedTask.class);
		RequestedTaskMeta setups = new RequestedTaskMeta( owner,target);
		PropertyAccessorUtil.setProperties(setups, map);
		setups.init();
		return setups;
	}
	
	public static ScheduledTaskMeta getScheduledTask(OwnerModelInfo owner, Method target) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(target, ScheduledTask.class);
		ScheduledTaskMeta setups =new ScheduledTaskMeta(owner, target);
		PropertyAccessorUtil.setProperties(setups, map);
		setups.init();
		return setups;
	}

	
	public static TaskMeta getSystemTask(OwnerModelInfo owner, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		setups.init(owner, method);
		return setups;
	}

	public static TaskMeta getAutoTask(OwnerModelInfo owner, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		if (StringUtil.isEmpty(setups.startTime) || StringUtil.isEmpty(setups.startDate) && (setups.initialDelay != 0)) {
			setups.init(owner, method);
		}
		return setups;
	}

	public static TaskMeta getOnDemandTask(OwnerModelInfo owner, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		setups.init(owner, method);
		return setups;
	}

	public static TaskMeta getDefualtTask(OwnerModelInfo owner, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		if (StringUtil.isEmpty(setups.startDate) || StringUtil.isEmpty(setups.startTime) && (setups.initialDelay == 0)) {
			setups.init(owner, method);
		}
		return setups;
	}

	public static TaskMeta getScheduleTask(OwnerModelInfo owner, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		if (StringUtil.isNonEmpty(setups.startDate) || StringUtil.isNonEmpty(setups.startTime)) {
			setups.init(owner, method);
		}
		return setups;
	}

}
