package org.brijframework.task.util;

import java.lang.reflect.Method;
import java.util.Map;

import org.brijframework.support.monitor.Task;
import org.brijframework.task.meta.TaskMeta;
import org.brijframework.util.reflect.AnnotationUtil;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.text.StringUtil;

public class TaskResourcesUtil {
	public static TaskMeta getSystemTask(Class<?> clazz, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		setups.init(clazz, method);
		return setups;
	}

	public static TaskMeta getAutoTask(Class<?> clazz, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		if (StringUtil.isEmpty(setups.startTime) || StringUtil.isEmpty(setups.startDate) && (setups.initialDelay != 0)) {
			setups.init(clazz, method);
		}
		return setups;
	}

	public static TaskMeta getOnDemandTask(Class<?> clazz, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		setups.init(clazz, method);
		return setups;
	}

	public static TaskMeta getDefualtTask(Class<?> clazz, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		if (StringUtil.isEmpty(setups.startDate) || StringUtil.isEmpty(setups.startTime) && (setups.initialDelay == 0)) {
			setups.init(clazz, method);
		}
		return setups;
	}

	public static TaskMeta getScheduleTask(Class<?> clazz, Method method) {
		Map<String, Object> map = AnnotationUtil.getAnnotationData(method, Task.class);
		TaskMeta setups = (TaskMeta) InstanceUtil.getInstance(TaskMeta.class, map);
		if (StringUtil.isNonEmpty(setups.startDate) || StringUtil.isNonEmpty(setups.startTime)) {
			setups.init(clazz, method);
		}
		return setups;
	}

}
