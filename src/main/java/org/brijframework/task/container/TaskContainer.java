package org.brijframework.task.container;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.brijframework.core.container.DefaultContainer;
import org.brijframework.group.Group;
import org.brijframework.support.monitor.Task;
import org.brijframework.task.meta.TaskMeta;
import org.brijframework.task.util.TaskResourcesUtil;
import org.brijframework.util.reflect.AnnotationUtil;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.MethodUtil;
import org.brijframework.util.reflect.PackUtil;

public class TaskContainer implements DefaultContainer{
	
	private static final String SYSTEM_TASK_CACHE = "SYSTEM_TASK_CACHE";
	private static final String AUTO_TASK_CACHE = "AUTO_TASK_CACHE";
	private static final String ON_DEMAND_TASK_CACHE = "ON_DEMAND_TASK_CACHE";
	private static final String SCHEDULE_TASK_CACHE = "SCHEDULE_TASK_CACHE";
	private static final String DEFAULT_TASK_CACHE = "DEFAULT_TASK_CACHE";
	private static final String AUTO_TASK = "AUTO_TASK";
	private static final String DEFAULT_TASK = "DEFAULT_TASK";
	private static final String SCHEDULE_TASK = "SCHEDULE_TASK";
	private static final String ON_DEMAND_TASK ="ON_DEMAND_TASK";
	private static TaskContainer container;
	public Map<String, Object> cacheKeys = new HashMap<>();
	@SuppressWarnings("rawtypes")
	private ConcurrentHashMap<String, ConcurrentHashMap> taskContainerCache = new ConcurrentHashMap<String, ConcurrentHashMap>();

	public static TaskContainer getContainer() {
		if (container == null) {
			container = (TaskContainer) InstanceUtil.getInstance(TaskContainer.class);
			container.loadContainer();
		}
		return container;
	}

	@Override
	public TaskContainer loadContainer() {
		this.cacheKeys.put(container.getClass().getSimpleName() + "_" + SYSTEM_TASK_CACHE, SYSTEM_TASK_CACHE);
		this.cacheKeys.put(container.getClass().getSimpleName() + "_" + AUTO_TASK_CACHE, AUTO_TASK_CACHE);
		this.cacheKeys.put(container.getClass().getSimpleName() + "_" + ON_DEMAND_TASK_CACHE, ON_DEMAND_TASK_CACHE);
		this.cacheKeys.put(container.getClass().getSimpleName() + "_" + SCHEDULE_TASK_CACHE, SCHEDULE_TASK_CACHE);
		this.cacheKeys.put(container.getClass().getSimpleName() + "_" + DEFAULT_TASK_CACHE, DEFAULT_TASK_CACHE);
		this.loadFromJAR();
		this.taskloader();
		return container;
	}

	private void loadFromJAR() {
		ConcurrentHashMap<String, Object> systemTask = new ConcurrentHashMap<>();
		this.taskContainerCache.put(SYSTEM_TASK_CACHE, systemTask);
	}

	private void taskloader() {
		Collection<Class<?>> USRclasses = PackUtil.getProjectClasses();

		ConcurrentHashMap<Object, Object> autoTask = new ConcurrentHashMap<>();
		ConcurrentHashMap<Object, Object> defaultTask = new ConcurrentHashMap<>();
		ConcurrentHashMap<Object, Object> scheduleTask = new ConcurrentHashMap<>();
		ConcurrentHashMap<Object, Object> onDemandTask = new ConcurrentHashMap<>();

		for (Class<?> clazz : USRclasses) {
			Collection<Method> methods = MethodUtil.getAllMethod(clazz);
			for (Method method : methods) {
				if (AnnotationUtil.isExistAnnotation(method, Task.class)) {
					TaskMeta taskSetups = TaskResourcesUtil.getAutoTask(clazz, method);
					if (taskSetups != null) {
						autoTask.put(taskSetups.getId(), taskSetups);
						System.err.format("BeanID-> %25s", taskSetups.getOwner().getSimpleName());
						System.err.format("   For Auto   Task ID => %25s \n", taskSetups.getId());
					}
				}
				if (AnnotationUtil.isExistAnnotation(method, Task.class)) {
					TaskMeta taskSetups = TaskResourcesUtil.getDefualtTask(clazz, method);
					if (taskSetups != null) {
						defaultTask.put(taskSetups.getId(), taskSetups);
						System.err.format("BeanID-> %25s", taskSetups.getOwner().getSimpleName());
						System.err.format("   For Default   Task ID => %25s \n", taskSetups.getId());
					}
				}
				if (AnnotationUtil.isExistAnnotation(method, Task.class)) {
					TaskMeta taskSetups = TaskResourcesUtil.getScheduleTask(clazz, method);
					if (taskSetups != null) {
						scheduleTask.put(taskSetups.getId(), taskSetups);
						System.err.format("BeanID-> %25s", taskSetups.getOwner().getSimpleName());
						System.err.format("   For Schedule Task ID => %25s \n", taskSetups.getId());
					}
				}
				if (AnnotationUtil.isExistAnnotation(method, Task.class)) {
					TaskMeta taskSetups = TaskResourcesUtil.getOnDemandTask(clazz, method);
					if (taskSetups != null) {
						onDemandTask.put(taskSetups.getId(), taskSetups);
						System.err.format("BeanID-> %25s", taskSetups.getOwner().getSimpleName());
						System.err.format("   For OnDemand Task ID => %25s \n", taskSetups.getId());
					}
				}
			}
		}

		this.taskContainerCache.put(AUTO_TASK, autoTask);
		this.taskContainerCache.put(DEFAULT_TASK, defaultTask);
		this.taskContainerCache.put(SCHEDULE_TASK, scheduleTask);
		this.taskContainerCache.put(ON_DEMAND_TASK, onDemandTask);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ConcurrentHashMap getCache() {
		return this.taskContainerCache;
	}

	@Override
	public Group load(Object groupKey) {
		// TODO Auto-generated method stub
		return null;
	}


}
